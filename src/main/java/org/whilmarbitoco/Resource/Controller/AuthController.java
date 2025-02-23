package org.whilmarbitoco.Resource.Controller;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.*;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.AuthService;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthController {

    @Inject
    AuthService service;

    @POST
    @Path("/signup")
    public Response signup(@Valid EmployeeDTO dto) {
        service.signup(dto.email, dto.password, dto.firstname, dto.lastname, dto.role);
        return Status.ok("Account Created. Please check your email to verify your account.");
    }

    @POST
    @Path("/login")
    public TokenDTO login(@Valid LoginDTO dto) {
        return service.authenticate(dto.email, dto.password);
    }

    @POST
    @Path("/verify-email")
    public Response verifyEmail(@Valid EmailDTO dto) {
        service.verifyEmail(dto.email, dto.code);
        return Status.ok("Email Verified");
    }

    @POST
    @Path("/generate-verification")
    public Response generateCode(@QueryParam("email") @Email String email) {
        service.generateVerification(email);
        return Status.ok("Please check your email to verify your account.");
    }


}
