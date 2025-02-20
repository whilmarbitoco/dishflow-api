package org.whilmarbitoco.Resource.Http;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.IngredientAddDTO;
import org.whilmarbitoco.Core.DTO.MenuDTO;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.MenuService;

@Path("/menu")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuResource {


    @Inject
    MenuService menuService;

    @POST
    @Path("/add")
    public Response add(@Valid MenuDTO dto) {
        menuService.createMenu(dto.name, dto.price, dto.description);
        return Status.ok("Menu Added.");
    }

    @POST
    @Path("/add/ingredients")
    public Response addIngredient(@Valid IngredientAddDTO dto) {
        menuService.addIngredient(dto.menuID, dto.ingredients);
        return Status.ok("Menu Updated. Ingredients Added.");
    }

}
