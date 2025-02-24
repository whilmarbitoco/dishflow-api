package org.whilmarbitoco.Resource.Controller;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.EmployeeDTO;
import org.whilmarbitoco.Core.DTO.EmployeeUpdateDTO;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.EmployeeService;

import java.util.List;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/employee")
public class EmployeeController {

    @Inject
    EmployeeService employeeService;


    @RolesAllowed({"Manager", "Chef", "Waiter"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    @Path("/update")
    public Response update(@QueryParam("id") @NotNull Long id, @Valid EmployeeUpdateDTO update) {
        employeeService.updateEmployee(id, update.email, update.firstname, update.lastname, update.image);
        return Status.ok("Employee Updated.");
    }

    @RolesAllowed("Manager")
    @GET
    public EmployeeDTO get(@QueryParam("id") @NotNull Long id) {
        return employeeService.getById(id);
    }

    @RolesAllowed("Manager")
    @GET
    @Path("/all")
    public List<EmployeeDTO> getAll() {
        return employeeService.getAll();
    }

}
