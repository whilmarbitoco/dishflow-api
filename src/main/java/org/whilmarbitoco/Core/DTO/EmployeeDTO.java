package org.whilmarbitoco.Core.DTO;

import jakarta.validation.constraints.NotBlank;

public class EmployeeDTO {

    @NotBlank
    public String email;

    @NotBlank
    public String password;

    @NotBlank
    public String firstname;

    @NotBlank
    public String lastname;

    @NotBlank
    public String role;
    public boolean verified;


    public EmployeeDTO(String email, String password, String firstname, String lastname, String role, boolean is_verified) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.verified = is_verified;
    }

}
