package org.whilmarbitoco.Core.DTO;

import io.quarkus.security.jpa.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @Email
    @NotBlank
    public String email;

    @Password
    @NotBlank
    public String password;

}
