package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.DTO.IngredientDTO;
import org.whilmarbitoco.Core.DTO.OrderDTO;
import org.whilmarbitoco.Core.Model.Ingredient;
import org.whilmarbitoco.Core.Model.Menu;
import org.whilmarbitoco.Core.Model.MenuIngredient;
import org.whilmarbitoco.Core.Model.Order;
import org.whilmarbitoco.Repository.IngredientRepository;
import org.whilmarbitoco.Repository.MenuIngredientRepository;

import java.util.List;

@ApplicationScoped
public class IngredientService {

    @Inject
    IngredientRepository ingredientRepository;
    @Inject
    MenuIngredientRepository menuIngRepository;
    @Inject
    MailService mailService;

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

        if (ingredient.getQuantity()  < 1) {
            mailService.notifyManager(ingredient.getName(), ingredient.getName() + " has ran out.");
            throw new BadRequestException(ingredient.getName() + " Quantity is not enough.");
        }

        if (quantity > ingredient.getQuantity()) {
            throw new BadRequestException(ingredient.getName() + " Quantity is not enough.");
        }

        if (ingredient.getQuantity() < 50) {
            mailService.notifyManager(ingredient.getName(), ingredient.getName() + " is getting low.");
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

    public Ingredient getById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id);
        if (ingredient == null) {
            throw new BadRequestException("Ingredient With ID " + id + " Not Found.");
        }
        return ingredient;
    }

    @Transactional
    public void checkQuantity(List<OrderDTO> orders) {
        for (OrderDTO o : orders) {
            List<Ingredient> ingredients = menuIngRepository.findIngredientsByMenuId(o.menuID);
            for (Ingredient i : ingredients) {
                MenuIngredient menuIngredient = menuIngRepository.findByIngredient(i);
                reduceQuantity(i.getId(), menuIngredient.getQuantityRequired());
            }
        }

    }

}
