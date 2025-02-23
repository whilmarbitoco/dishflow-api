package org.whilmarbitoco.Core.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

public class EmployeeDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long id;

    @NotBlank
    public String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    public String password;

    @NotBlank
    public String firstname;

    @NotBlank
    public String lastname;

    @NotBlank
    public String role;

    public String photo;

    public boolean verified;

}
