package org.whilmarbitoco.Service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.DTO.EmployeeDTO;
import org.whilmarbitoco.Core.DTO.LoginDTO;
import org.whilmarbitoco.Core.DTO.TokenDTO;
import org.whilmarbitoco.Core.DTO.VerifyEmailDTO;
import org.whilmarbitoco.Core.Model.Employee;
import org.whilmarbitoco.Core.Model.Role;
import org.whilmarbitoco.Core.Model.User;
import org.whilmarbitoco.Repository.EmployeeRepository;
import org.whilmarbitoco.Repository.RoleRepository;
import org.whilmarbitoco.Repository.UserRepository;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    RoleRepository roleRepository;

    @Inject
    TokenService tokenService;

    @Inject
    EmailVerificationService emailVerificationService;

    @Transactional
    public void signup(EmployeeDTO empDTO) {
        if (empDTO.getEmail() == null || !empDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BadRequestException("Invalid email format.");
        }

        if (userRepository.findByEmail(empDTO.getEmail()) != null) {
            throw new BadRequestException("Email Already Registered.");
        }

        Role role = roleRepository.findByName(empDTO.getRole());
        if (role == null) {
            throw new BadRequestException("Invalid Role: " + empDTO.getRole());
        }

        String hashedPassword = BcryptUtil.bcryptHash(empDTO.getPassword());
        User user = new User(empDTO.getEmail(), hashedPassword);
        userRepository.persist(user);

        Employee employee = new Employee(empDTO.getFirstname(), empDTO.getLastname());
        employee.setUser(user);
        employee.setRole(role);

        employeeRepository.persist(employee);
        emailVerificationService.sendVerification(empDTO.getEmail());
    }

    public TokenDTO authenticate(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.email);
        if (user == null) {
            throw new BadRequestException("Email Not Fount.");
        }

        if (!BcryptUtil.matches(loginDTO.password, user.getPassword())) {
            throw new BadRequestException("Invalid Username or Password.");
        }

        TokenDTO token = new TokenDTO();
        Set<String> role = Set.of(user.getEmployee().getRole().getRole());

        token.accessToken = tokenService.generateAccessToken(user.getEmail(), role);
        return token;
    }

    public void verifyEmail(VerifyEmailDTO dto) {
        User user = userRepository.findByEmail(dto.email);
        if (user == null) {
            throw new BadRequestException("Email Not Found.");
        }

        emailVerificationService.verifyCode(dto.email, dto.code);

//        TODO: change user.is_verified = true
    }

    public void generateVerification(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.email);
        if (user == null) {
            throw new BadRequestException("Email Not Found.");
        }

//        TODO: Check if user.is_verified = false

        emailVerificationService.sendVerification(loginDTO.email);
    }

    public List<EmployeeDTO> getAll() {
        return employeeRepository.listAll().stream().map(employee -> {
           return new EmployeeDTO(employee.getUser().getEmail(), employee.getUser().getPassword(), employee.getFirstname(), employee.getLastname(), employee.getRole().getRole());
        }).toList();
    }
}
