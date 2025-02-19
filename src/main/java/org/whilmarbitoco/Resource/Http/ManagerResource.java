package org.whilmarbitoco.Resource.Http;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.TableDTO;
import org.whilmarbitoco.Service.TableService;

import java.util.Map;

@Path("/manager")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ManagerResource {

    @Inject
    TableService tableService;

    @POST
    @Path("/add-table")
    public Response addTable(TableDTO table) {
        tableService.addTable(table);
        return Response.status(Response.Status.CREATED)
                .entity(Map.of("result", "Table Created."))
                .build();
    }

}
