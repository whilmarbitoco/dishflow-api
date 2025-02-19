package org.whilmarbitoco.Resource.Http;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.TableDTO;
import org.whilmarbitoco.Core.Model.Tables;
import org.whilmarbitoco.Repository.TableRepository;
import org.whilmarbitoco.Service.TableService;

import java.util.List;
import java.util.Map;


@Path("/waiter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WaiterResource {

    @Inject
    TableService tableService;
    @Inject
    TableRepository tableRepository;

    @GET
    @Path("/tables")
    public List<Tables> getTables() {
        return tableRepository.listAll();
    }

    @POST
    @Path("update-table")
    public Response updateTable(TableDTO dto) {
        tableService.updateTable(dto);
        return Response.status(Response.Status.CREATED)
                .entity(Map.of("result", "Table updated."))
                .build();
    }
}
