package org.whilmarbitoco.Core.DTO;

import jakarta.validation.constraints.NotNull;

public class OrderDTO {

    @NotNull
    public Long menuID;

    @NotNull
    public int quantity;


}
