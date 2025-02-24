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

import java.util.*;
import java.util.stream.Collectors;

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

        List<Ingredient> existingIngredients = menuIngRepository.findIngredientsByMenuId(menuID);

        Map<Long, Ingredient> existingIngredientMap = existingIngredients.stream()
                .collect(Collectors.toMap(Ingredient::getId, ingredient -> ingredient));

        for (MenuIngredientDTO dto : ingredients) {
            if (existingIngredientMap.containsKey(dto.ingredient)) {
                Ingredient duplicate = existingIngredientMap.get(dto.ingredient);
                throw new BadRequestException("Ingredient already exists: " + duplicate.getName());
            }
        }

        for (MenuIngredientDTO i : ingredients) {
            Ingredient ingredient = ingredientService.getById(i.ingredient);
            if (ingredient == null) {
                throw new BadRequestException("Ingredients with ID " + i.ingredient + " not found.");
            }
            MenuIngredient menuIngredient = new MenuIngredient(menu, ingredient, i.quantity);
            listIngredients.add(menuIngredient);
        }

        menu.setIngredients(listIngredients);
        menuRepository.updateAvailability(menuID, true);
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
                    m.available = menu.isAvailable();
                    return m;
                })
                .toList();
    }

    public boolean available(Long id) {
        return getMenu(id).isAvailable();
    }

    public List<MenuDTO> getAllAvailable() {
        return menuRepository.listAll().stream()
                .filter(Menu::isAvailable)
                .map(menu -> {
                    MenuDTO m = new MenuDTO();
                    m.id = menu.getId();
                    m.name = menu.getName();
                    m.price = menu.getPrice();
                    m.description = menu.getDescription();
                    m.img = menu.getImage();
                    m.available = menu.isAvailable();
                    return m;
                })
                .toList();
    }


    @Transactional
    public void validate(Menu menu) {
       List<MenuIngredient> mis = menu.getIngredients();

       for (MenuIngredient mi : mis) {
           Ingredient ing = mi.getIngredient();
           if (mi.getQuantityRequired() > ing.getQuantity()) {
               menu.setAvailable(false);
           }
       }
    }

}
