package org.whilmarbitoco.Core.DTO;


import java.time.LocalDate;

public class IngredientDTO {

    public Long id;

    public String name;

    public int quantity;

    public String unit;

    public LocalDate created_at;

    public LocalDate updated_at;

    public IngredientDTO(Long id, String name, int quantity, String unit, LocalDate created_at, LocalDate updated_at) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
