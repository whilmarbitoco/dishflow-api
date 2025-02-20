package org.whilmarbitoco.Core.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class MenuDTO {

    @NotBlank
    public String name;

    @PositiveOrZero
    public double price;

    @NotBlank
    public String description;

}
