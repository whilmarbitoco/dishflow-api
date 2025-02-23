package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.DTO.IngredientDTO;
import org.whilmarbitoco.Core.DTO.OrderDTO;
import org.whilmarbitoco.Core.Model.Ingredient;
import org.whilmarbitoco.Core.Model.MenuIngredient;
import org.whilmarbitoco.Repository.IngredientRepository;
import org.whilmarbitoco.Repository.MenuIngredientRepository;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class IngredientService {

    @Inject
    IngredientRepository ingredientRepository;
    @Inject
    MenuIngredientRepository menuIngRepository;
    @Inject
    MenuService menuService;
    @Inject
    MailService mailService;

    public List<IngredientDTO> getAll() {
        return ingredientRepository.listAll().stream()
                .map(ingredient -> {
                    IngredientDTO dto = new IngredientDTO();
                    dto.id = ingredient.getId();
                    dto.name = ingredient.getName();
                    dto.quantity = ingredient.getQuantity();
                    dto.unit = ingredient.getUnit();
                    dto.created_at = ingredient.getCreated_at();
                    dto.updated_at = ingredient.getUpdated_at();
                    return dto;
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
            menuService.updateAvailability(ingredient, false);
            throw new BadRequestException(ingredient.getName() + " Quantity is not enough.");
        }

        if (ingredient.getQuantity() < 50) {
            mailService.notifyManager(ingredient.getName(), ingredient.getName() + " is getting low.");
        }

        int qty = ingredient.getQuantity() - quantity;
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
    public void validateQuantity(List<OrderDTO> orders) {
        for (OrderDTO o : orders) {
            List<Ingredient> ingredients = menuIngRepository.findIngredientsByMenuId(o.menuID);
            for (Ingredient i : ingredients) {
                MenuIngredient menuIngredient = menuIngRepository.findByIngredient(i);
                reduceQuantity(i.getId(), (menuIngredient.getQuantityRequired() * o.quantity));
            }
        }
    }

    @Transactional
    public void update(Long id, String name, int quantity, String unit) {
        Ingredient ingredient = getById(id);
        Ingredient checkName = ingredientRepository.getByName(name);

        if (checkName != null && !Objects.equals(ingredient.getId(), checkName.getId())) {
            throw new BadRequestException("Ingredients " + name + " already in the database.");
        }

        if (!ingredient.getName().equals(name)) {
            ingredient.setName(name);
        }

        ingredient.setQuantity(quantity);
        ingredient.setUnit(unit);
    }
}
