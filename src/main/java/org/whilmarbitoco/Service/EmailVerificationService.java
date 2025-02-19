package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.Verification;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;


@ApplicationScoped
public class EmailVerificationService {

    @Inject
    private MailService mailService;

    private final ConcurrentHashMap<String, Verification> verificationCodes = new ConcurrentHashMap<>();

    private String generateVerificationCode() {
       final SecureRandom RANDOM = new SecureRandom();
        byte[] randomBytes = new byte[4];
        RANDOM.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private void storeCode(String email, String code) {
        if (verificationCodes.get(email) != null) {
            verificationCodes.remove(email);
        }
        verificationCodes.put(email, new Verification(code, LocalDateTime.now().plusMinutes(15)));
    }


    public void verifyCode(String email, String code) {
        Verification entry = verificationCodes.get(email);

        if (entry == null || entry.expiry.isBefore(LocalDateTime.now())) {
            verificationCodes.remove(email);
            throw new BadRequestException("Invalid Verification Code.");
        }

        if (!entry.code.equals(code)) {
            throw new BadRequestException("Invalid Verification Code.");
        }

        verificationCodes.remove(email);
    }

    public void sendVerification(String email) {
        String code = generateVerificationCode();
        String subject = "DishFlow Verification Code";
        String content = "Your verification code is: " + code;
        storeCode(email, code);

        mailService.sendEmail(email, subject, content);
    }

}
