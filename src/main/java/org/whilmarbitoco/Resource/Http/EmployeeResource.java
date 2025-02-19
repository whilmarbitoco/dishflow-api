package org.whilmarbitoco.Resource.Http;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.EmployeeDTO;
import org.whilmarbitoco.Core.DTO.LoginDTO;
import org.whilmarbitoco.Core.DTO.TokenDTO;
import org.whilmarbitoco.Core.Model.Employee;
import org.whilmarbitoco.Service.EmployeeService;

import java.util.List;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/employee")
public class EmployeeResource {

    @Inject
    EmployeeService service;

    @POST
    @Path("/signup")
    public Response signup(EmployeeDTO dto) {
        service.create(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("login")
    public TokenDTO login(LoginDTO dto) {
        return service.authenticate(dto);
    }


}
