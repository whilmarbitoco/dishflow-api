package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.whilmarbitoco.Core.DTO.MenuDTO;
import org.whilmarbitoco.Core.DTO.MenuIngredientDTO;
import org.whilmarbitoco.Core.Model.Ingredient;
import org.whilmarbitoco.Core.Model.Menu;
import org.whilmarbitoco.Core.Model.MenuIngredient;
import org.whilmarbitoco.Repository.MenuIngredientRepository;
import org.whilmarbitoco.Repository.MenuRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MenuService {

    @Inject
    MenuRepository menuRepository;
    @Inject
    IngredientService ingredientService;
    @Inject
    MenuIngredientRepository menuIngRepository;
    @Inject
    ImageService imageService;


    @Transactional
    public void createMenu(String name, double price, String description, FileUpload image) {
        imageService.validate(image);
        if ( menuRepository.findByName(name) != null) {
            throw new BadRequestException(name + " is already on the menu.");
        }

        String file = imageService.saveFile(image);

        Menu newMenu = new Menu(name, price, description, file);
        menuRepository.persist(newMenu);
    }

    @Transactional
    public void addIngredient(Long menuID, List<MenuIngredientDTO> ingredients) {
        Menu menu = menuRepository.findById(menuID);
        List<MenuIngredient> listIngredients = new ArrayList<>();

        if (menu == null) {
            throw new BadRequestException("Menu with ID " + menuID + " not found.");
        }

        for (MenuIngredientDTO i : ingredients) {
            Ingredient ingredient = ingredientService.getById(i.ingredient);
            if (ingredient == null) {
                throw new BadRequestException("Ingredients with ID " + i.ingredient + " not found.");
            }
            if (menuIngRepository.findByIngredient(ingredient) != null) {
                throw new BadRequestException("Ingredient with ID " + i.ingredient + " is already added.");
            }
            MenuIngredient menuIngredient = new MenuIngredient(menu, ingredient, i.quantity);
            listIngredients.add(menuIngredient);
        }

        menu.setIngredients(listIngredients);
        menuRepository.persist(menu);
    }

    public Menu getMenu(Long menuID) {
        Menu menu = menuRepository.findById(menuID);
        if (menu == null) {
            throw new BadRequestException("Menu with ID " + menuID + " not found.");
        }

        return menu;
    }

    public List<MenuDTO> getAll() {
        return menuRepository.listAll().stream()
                .map(menu -> {
                    MenuDTO m = new MenuDTO();
                    m.id = menu.getId();
                    m.name = menu.getName();
                    m.price = menu.getPrice();
                    m.description = menu.getDescription();
                    m.img = menu.getImage();
                    return m;
                })
                .toList();
    }
}
