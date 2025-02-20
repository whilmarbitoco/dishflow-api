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
                .map(ingd -> {
                    return new IngredientDTO(ingd.getId(), ingd.getName(), ingd.getQuantity(), ingd.getUnit(), ingd.getCreated_at(), ingd.getUpdated_at());
                }).toList();
    }

    @Transactional
    public void create(IngredientDTO dto) {
        if (ingredientRepository.getByName(dto.name) != null) {
            throw new BadRequestException("Ingredients " + dto.name + " already in the database.");
        }

        Ingredient ingredient = new Ingredient(dto.name, dto.quantity, dto.unit);
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

}
