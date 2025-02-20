package org.whilmarbitoco.Resource.Http;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.IngredientDTO;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.IngredientService;

import java.util.List;

@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryResource {

    @Inject
    IngredientService ingredientService;

    @GET
    @Path("/all-ingredients")
    public List<IngredientDTO> getAllIngredients() {
        return ingredientService.getAll();
    }

    @POST
    @Path("/add-ingredient")
    public Response addIngredients(IngredientDTO dto) {
        ingredientService.create(dto);
        return Status.ok("Ingredients Created.");
    }

}
