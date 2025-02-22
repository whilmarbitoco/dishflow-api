package org.whilmarbitoco.Core.DTO;

import jakarta.validation.constraints.Positive;

public class MenuIngredientDTO {


    public Long ingredient;

    @Positive
    public int quantity;

}
