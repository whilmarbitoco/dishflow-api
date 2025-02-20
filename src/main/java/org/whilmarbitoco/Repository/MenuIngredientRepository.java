package org.whilmarbitoco.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.whilmarbitoco.Core.Model.Ingredient;
import org.whilmarbitoco.Core.Model.MenuIngredient;

@ApplicationScoped
public class MenuIngredientRepository implements PanacheRepository<MenuIngredient> {

    public MenuIngredient findByIngredient(Ingredient ingredient) {
        return find("ingredient", ingredient).firstResult();
    }
}
