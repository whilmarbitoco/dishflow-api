package org.whilmarbitoco.Core.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class MenuDTO {

    public Long id;

    @NotBlank(message = "name is required")
    @FormParam("name")
    public String name;

    @Positive(message = "price cannot be zero or negative")
    @FormParam("price")
    public double price;

    @NotBlank(message = "Description is required")
    @FormParam("description")
    public String description;

    @FormParam("image")
    @NotNull
    public FileUpload image;

    public String img;
}
