package org.whilmarbitoco.Service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.DTO.TokenDTO;
import org.whilmarbitoco.Core.Model.Role;
import org.whilmarbitoco.Core.Model.User;
import org.whilmarbitoco.Repository.RoleRepository;

import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    EmployeeService employeeService;

    @Inject
    UserService userService;
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
        User user = userService.getByEmail(email);
        if (user != null) {
            throw new BadRequestException("Email Already Registered.");
        }

        Role empRole = roleRepository.findByName(role);
        if (empRole == null) {
            throw new BadRequestException("Invalid Role: " + role);
        }

        String hashedPassword = BcryptUtil.bcryptHash(password);
        employeeService.create(email, hashedPassword, firstname, lastname, empRole);
        emailVerificationService.sendVerification(email);
    }

    public TokenDTO authenticate(String email, String password) {
        validateEmail(email);
        User user = userService.getByEmail(email);
        if (user == null) {
            throw new BadRequestException("Email Not Found.");
        }

        if (!user.IsVerified()) {
            throw new BadRequestException("User is Not Verified.");
        }

        if (!BcryptUtil.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid Username or Password.");
        }

        TokenDTO token = new TokenDTO();
        Set<String> role = Set.of(user.getEmployee().getRole().getRole());

        token.accessToken = tokenService.generateAccessToken(user.getEmail(), role);
        token.refreshToken = tokenService.generateRefreshToken(user.getEmail());
        return token;
    }

    public void verifyEmail(String email, String code) {
        validateEmail(email);
        User user = userService.getByEmail(email);
        if (user == null) {
            throw new BadRequestException("Email Not Found.");
        }

        emailVerificationService.verifyCode(email, code);
        userService.verifyUser(user.getId());
    }

    public void generateVerification(String email) {
        validateEmail(email);
        User user = userService.getByEmail(email);
        if (user == null) {
            throw new BadRequestException("Email Not Found.");
        }

        if (user.IsVerified()) {
            throw new BadRequestException("User Already Verified.");
        }

        emailVerificationService.sendVerification(email);
    }


    private void validateEmail(String email) {
        if  (!mailService.validate(email)) {
            throw new BadRequestException("Invalid email format.");
        }
    }

}
