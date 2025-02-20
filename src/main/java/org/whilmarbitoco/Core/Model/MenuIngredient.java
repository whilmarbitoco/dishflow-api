package org.whilmarbitoco.Core.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "MenuIngredient")
public class MenuIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "Menu", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private Menu menus;

    @ManyToMany(mappedBy = "Ingredient", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<Ingredient> ingredients;

    @Column(name = "quantity_required")
    private int quantity_required;

    public MenuIngredient() {}

    public MenuIngredient(Menu menus, List<Ingredient> ingredients, int quantity_required) {
        this.menus = menus;
        this.ingredients = ingredients;
        this.quantity_required = quantity_required;
    }

    public Long getId() {
        return id;
    }

    public Menu getMenus() {
        return menus;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getQuantity_required() {
        return quantity_required;
    }

    public void setMenus(Menu menus) {
        this.menus = menus;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setQuantity_required(int quantity_required) {
        this.quantity_required = quantity_required;
    }
}
