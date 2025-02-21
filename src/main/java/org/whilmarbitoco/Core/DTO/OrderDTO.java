package org.whilmarbitoco.Core.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class OrderDTO {

    @NotBlank
    public Long menuID;

    @Positive
    public int quantity;
}
