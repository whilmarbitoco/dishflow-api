package org.whilmarbitoco.Resource.Controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.TableDTO;
import org.whilmarbitoco.Core.Model.Menu;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Repository.MenuRepository;
import org.whilmarbitoco.Service.MenuService;
import org.whilmarbitoco.Service.TableService;

import java.util.List;

@Path("/table")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TableController {

    @Inject
    TableService tableService;
    @Inject
    MenuService menuService;

    @Inject
    MenuRepository rp;



    @RolesAllowed("Manager")
    @POST
    @Path("/add")
    public Response addTable(TableDTO dto) {
        tableService.addTable(dto.number, dto.capacity);
        return Status.ok("Table Created.");
    }

    @RolesAllowed({"Manager", "Waiter"})
    @POST
    @Path("/update")
    public Response updateTable(TableDTO dto) {
        tableService.updateTable(dto.number, dto.status);
        return Status.ok("Table Updated.");
    }

    @RolesAllowed({"Manager", "Chef", "Waiter"})
    @GET
    @Path("/all")
    public List<TableDTO> all() {
        return tableService.getAll();
    }

    @RolesAllowed({"Manager", "Chef", "Waiter"})
    @GET
    @Path("/all/available")
    public List<TableDTO> allAvailable() {
        return tableService.getAllAvailable();
    }


}

