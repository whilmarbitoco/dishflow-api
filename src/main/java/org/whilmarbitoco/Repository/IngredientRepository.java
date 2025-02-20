package org.whilmarbitoco.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.whilmarbitoco.Core.Model.Ingredient;

@ApplicationScoped
public class IngredientRepository implements PanacheRepository<Ingredient> {

    public Ingredient getByName(String name) {
        return find("name", name).firstResult();
    }
}
