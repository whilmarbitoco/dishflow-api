package org.whilmarbitoco.Core.DTO;

import jakarta.validation.constraints.PositiveOrZero;

public class MenuIngredientDTO {


    public Long ingredient;

    @PositiveOrZero
    public int quantity;

}
