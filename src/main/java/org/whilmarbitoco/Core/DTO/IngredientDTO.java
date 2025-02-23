package org.whilmarbitoco.Core.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class IngredientDTO {

    public Long id;

    @NotBlank
    public String name;

    @NotNull
    @Positive
    public int quantity;

    @NotBlank
    public String unit;

    public LocalDate created_at;

    public LocalDate updated_at;

}
