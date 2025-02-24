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
import org.whilmarbitoco.Repository.IngredientRepository;
import org.whilmarbitoco.Repository.MenuIngredientRepository;
import org.whilmarbitoco.Repository.MenuRepository;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class IngredientService {

    @Inject
    MenuRepository menuRepository;
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
    public void updateQuantity(List<OrderDTO> orders) {
        for (OrderDTO o : orders) {
            List<Ingredient> ingredients = menuIngRepository.findIngredientsByMenuId(o.menuID);
            for (Ingredient i : ingredients) {
                MenuIngredient menuIngredient = menuIngRepository.findByIngredient(i);
                int qty = menuIngredient.getQuantityRequired() * o.quantity;
                menuIngredient.getIngredient().setQuantity(i.getQuantity() - qty);
            }
        }
    }

    public Ingredient getById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id);
        if (ingredient == null) {
            throw new BadRequestException("Ingredient With ID " + id + " Not Found.");
        }
        return ingredient;
    }

    @Transactional //(dontRollbackOn = BadRequestException.class)
    public void validateQuantity(List<OrderDTO> orders) {
        for (OrderDTO o : orders) {
            List<Ingredient> ingredients = menuIngRepository.findIngredientsByMenuId(o.menuID);

            for (Ingredient i : ingredients) {
                MenuIngredient menuIngredient = menuIngRepository.findByIngredient(i);
                if (i.getQuantity() < 50) {
                    mailService.notifyManager(i.getName(), i.getName() + " has is getting low.");
                }

                if (i.getQuantity()  == 0) {
                    mailService.notifyManager(i.getName(), i.getName() + " has ran out.");
                }

               if ((menuIngredient.getQuantityRequired() * o.quantity) > i.getQuantity()) {
                   menuRepository.updateAvailability(menuIngredient.getMenu().getId(), false);
                   throw new BadRequestException(i.getName() + " Quantity is not enough.");
               }
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

    public List<Ingredient> getAllByMenu(Long id) {
        return menuIngRepository.findIngredientsByMenuId(id);
    }
}
