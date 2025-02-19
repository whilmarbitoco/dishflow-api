package org.whilmarbitoco.Service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class TokenService {

    public String generateAccessToken(String username, Set<String> role) {
        return Jwt.issuer("https://bill-share.com")
                .upn(username).groups(role)
                .expiresAt(System.currentTimeMillis() / 1000 + 3600)
                .sign();
    }
}
