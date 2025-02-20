package org.whilmarbitoco.Resource.Http;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.annotations.Param;
import org.whilmarbitoco.Core.DTO.*;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.AuthService;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

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
    public Response verifyEmail(@Valid VerifyEmailDTO dto) {
        service.verifyEmail(dto.email, dto.code);
        return Status.ok("Email Verified");
    }

    @POST
    @Path("/generate-verification")
    public Response generateCode(@Valid EmailDTO emailDTO) {
        service.generateVerification(emailDTO.email);
        return Status.ok("Please check your email to verify your account.");
    }


}
