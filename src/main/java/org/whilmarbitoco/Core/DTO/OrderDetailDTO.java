package org.whilmarbitoco.Core.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.whilmarbitoco.Core.utils.OrderStatus;

import java.util.List;

public class OrderDetailDTO {

    @NotNull
    public int tableNumber;
    @NotNull
    public Long waiterID;
    public OrderStatus status;
    @NotNull
    public String notes;

    public List<OrderDTO> orders;

}
