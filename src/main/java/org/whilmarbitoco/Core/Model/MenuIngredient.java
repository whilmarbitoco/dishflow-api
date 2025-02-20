package org.whilmarbitoco.Core.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "MenuIngredient")
public class MenuIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "quantity_required", nullable = false)
    private int quantityRequired;

    public MenuIngredient() {}

    public MenuIngredient(Menu menu, Ingredient ingredient, int quantityRequired) {
        this.menu = menu;
        this.ingredient = ingredient;
        this.quantityRequired = quantityRequired;
    }

    public Long getId() {
        return id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(int quantityRequired) {
        this.quantityRequired = quantityRequired;
    }
}
