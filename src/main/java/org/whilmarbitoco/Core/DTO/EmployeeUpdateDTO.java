package org.whilmarbitoco.Core.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class EmployeeUpdateDTO {

    @FormParam("email")
    @Email
    @NotBlank
    public String email;
    @FormParam("firstname")
    @NotBlank
    public String firstname;
    @FormParam("lastname")
    @NotBlank
    public String lastname;
    @FormParam("image")
    @NotNull
    public FileUpload image;
}
