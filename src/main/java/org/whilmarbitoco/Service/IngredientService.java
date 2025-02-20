package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.DTO.IngredientDTO;
import org.whilmarbitoco.Core.Model.Ingredient;
import org.whilmarbitoco.Repository.IngredientRepository;

import java.util.List;

@ApplicationScoped
public class IngredientService {

    @Inject
    IngredientRepository ingredientRepository;


    public List<IngredientDTO> getAll() {
        return ingredientRepository.listAll().stream()
                .map(ingredient -> {
                    return new IngredientDTO(ingredient.getId(), ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit(), ingredient.getCreated_at(), ingredient.getUpdated_at());
                }).toList();
    }

    @Transactional
    public void create(String name, int quantity, String unit) {
        if (ingredientRepository.getByName(name) != null) {
            throw new BadRequestException("Ingredients " + name + " already in the database.");
        }

        Ingredient ingredient = new Ingredient(name, quantity, unit);
        ingredientRepository.persist(ingredient);
    }

    @Transactional
    public void reduceQuantity(Long id, int quantity) {
        Ingredient ingredient = ingredientRepository.findById(id);
        if (ingredient == null) {
            throw new BadRequestException("Ingredient ID not found.");
        }

        if (ingredient.getQuantity() == 0 || quantity > ingredient.getQuantity()) {
            throw new BadRequestException("Ingredient Quantity is too small.");
        }

        int qty = ingredient.getQuantity() - quantity;
        ingredient.setQuantity(qty);
    }

    @Transactional
    public void addQuantity(Long id, int quantity) {
        if (quantity <= 0) {
            throw new BadRequestException("Quantity cannot be less than 0.");
        }

        Ingredient ingredient = ingredientRepository.findById(id);
        if (ingredient == null) {
            throw new BadRequestException("Ingredient ID not found.");
        }

        int qty = ingredient.getQuantity() + quantity;
        ingredient.setQuantity(qty);
    }

    public Ingredient findById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id);
        if (ingredient == null) {
            throw new BadRequestException("Ingredient With ID " + id + " Not Found.");
        }

        return ingredient;
    }

}
