package org.whilmarbitoco.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.whilmarbitoco.Core.Model.Ingredient;
import org.whilmarbitoco.Core.Model.Menu;
import org.whilmarbitoco.Core.Model.MenuIngredient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MenuIngredientRepository implements PanacheRepository<MenuIngredient> {

    public MenuIngredient findByIngredient(Ingredient ingredient) {
        return find("ingredient", ingredient).firstResult();
    }

    public List<Ingredient> findIngredientsByMenuId(Long menuId) {
        List<Ingredient> ingredients = new ArrayList<>();
        List<MenuIngredient> menuIngredients = find("menu.id", menuId).list();

        for (MenuIngredient menuIngredient : menuIngredients) {
            ingredients.add(menuIngredient.getIngredient());
        }

        return ingredients;
    }

    public MenuIngredient findByMenu(Menu menu) {
        return find("menu", menu).firstResult();
    }

    public List<Menu> findMenusByIngredient(Ingredient ingredient) {
        return find("ingredient", ingredient).stream()
                .map(MenuIngredient::getMenu)
                .toList();
    }
}
