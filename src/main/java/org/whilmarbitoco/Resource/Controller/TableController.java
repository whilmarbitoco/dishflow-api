package org.whilmarbitoco.Resource.Controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.TableDTO;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.TableService;

@Path("/table")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TableController {

    @Inject
    TableService tableService;

    @POST
    @Path("/add")
    public Response addTable(TableDTO dto) {
        tableService.addTable(dto.number, dto.capacity);
        return Status.ok("Table Created.");
    }

    @POST
    @Path("/update")
    public Response updateTable(TableDTO dto) {
        tableService.updateTable(dto.number, dto.status);
        return Status.ok("Table Updated.");
    }

//  TODO:   get all table

}

