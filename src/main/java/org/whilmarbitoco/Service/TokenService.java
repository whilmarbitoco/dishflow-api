package org.whilmarbitoco.Service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class TokenService {

    public String generateAccessToken(String email, Set<String> roles) {
        return Jwt.issuer("https://bill-share.com")
                .upn(email)
                .groups(roles)
                .claim("token_type", "access")
                .expiresAt(System.currentTimeMillis() / 1000 + 3600) // 1 hour expiration
                .sign();
    }

    public String generateRefreshToken(String email) {
        return Jwt.issuer("https://bill-share.com")
                .upn(email)
                .groups(Set.of("refresh"))
                .claim("token_type", "refresh")
                .expiresAt(System.currentTimeMillis() / 1000 + (7 * 24 * 3600)) // 7 days expiration
                .sign();
    }

}
