package org.whilmarbitoco.Resource.Controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
    public List<IngredientDTO> all() {
        return ingredientService.getAll();
    }

    @POST
    @Path("/add")
    public Response add(@Valid IngredientDTO dto) {
        ingredientService.create(dto.name, dto.quantity, dto.unit);
        return Status.ok("Ingredients Created.");
    }

    @POST
    @Path("/update")
    public Response update(@QueryParam("id") Long id, IngredientDTO dto) {
        ingredientService.update(id, dto.name, dto.quantity, dto.unit);
        return Status.ok("Ingredient updated.");
    }

}
