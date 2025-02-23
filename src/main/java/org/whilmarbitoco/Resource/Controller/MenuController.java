package org.whilmarbitoco.Resource.Controller;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.IngredientAddDTO;
import org.whilmarbitoco.Core.DTO.MenuDTO;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.MenuService;

import java.util.List;

@Path("/menu")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuController {


    @Inject
    MenuService menuService;

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    @Path("/add")
    public Response add(@Valid MenuDTO dto) {
        menuService.createMenu(dto.name, dto.price, dto.description, dto.image);
        return Status.ok("Menu Added.");
    }

    @POST
    @Path("/add/ingredients")
    public Response addIngredient(@Valid IngredientAddDTO dto) {
        menuService.addIngredient(dto.menuID, dto.ingredients);
        return Status.ok("Menu Updated. Ingredients Added.");
    }

    @GET
    @Path("/all")
    public List<MenuDTO> all() {
        return menuService.getAll();
    }
}
