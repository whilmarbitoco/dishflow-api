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

    @Inject
    MailService mailService;

    @Transactional
    public void signup(String email, String password, String firstname, String lastname, String role) {
        validateEmail(email);

        if (userRepository.findByEmail(email) != null) {
            throw new BadRequestException("Email Already Registered.");
        }

        Role empRole = roleRepository.findByName(role);
        if (empRole == null) {
            throw new BadRequestException("Invalid Role: " + role);
        }

        String hashedPassword = BcryptUtil.bcryptHash(password);
        User user = new User(email, hashedPassword);
        userRepository.persist(user);

        Employee employee = new Employee(firstname, lastname);
        employee.setUser(user);
        employee.setRole(empRole);

        employeeRepository.persist(employee);
        emailVerificationService.sendVerification(email);
    }

    public TokenDTO authenticate(String email, String password) {
        validateEmail(email);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new BadRequestException("Email Not Fount.");
        }

        if (!BcryptUtil.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid Username or Password.");
        }

        TokenDTO token = new TokenDTO();
        Set<String> role = Set.of(user.getEmployee().getRole().getRole());

        token.accessToken = tokenService.generateAccessToken(user.getEmail(), role);
        return token;
    }

    public void verifyEmail(String email, String code) {
        validateEmail(email);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new BadRequestException("Email Not Found.");
        }

        emailVerificationService.verifyCode(email, code);
        userRepository.verifyUser(user.getId());
    }

    public void generateVerification(String email) {
        validateEmail(email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new BadRequestException("Email Not Found.");
        }

        if (user.IsVerified()) {
            throw new BadRequestException("User Already Verified.");
        }

        emailVerificationService.sendVerification(email);
    }

    public List<EmployeeDTO> getAll() {
        return employeeRepository.listAll().stream().map(employee -> {
           return new EmployeeDTO(employee.getUser().getEmail(), employee.getUser().getPassword(), employee.getFirstname(), employee.getLastname(), employee.getRole().getRole(), employee.getUser().IsVerified());
        }).toList();
    }


    private void validateEmail(String email) {
        if  (!mailService.validate(email)) {
            throw new BadRequestException("Invalid email format.");
        }
    }

}
