package org.whilmarbitoco.Resource.Controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.IngredientDTO;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.IngredientService;

import java.util.List;

@Path("/ingredient")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IngredientController {

    @Inject
    IngredientService ingredientService;

    @GET
    @Path("/all")
    public List<IngredientDTO> getAllIngredients() {
        return ingredientService.getAll();
    }

    @POST
    @Path("/add")
    public Response addIngredients(IngredientDTO dto) {
        ingredientService.create(dto.name, dto.quantity, dto.unit);
        return Status.ok("Ingredients Created.");
    }

//    Update



}
