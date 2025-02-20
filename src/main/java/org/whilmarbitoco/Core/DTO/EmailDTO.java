package org.whilmarbitoco.Core.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {

    @Email
    @NotBlank
    public String email;

}
