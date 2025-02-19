package org.whilmarbitoco.Resource.Http;

import io.quarkus.mailer.Mailer;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.EmployeeDTO;
import org.whilmarbitoco.Core.DTO.LoginDTO;
import org.whilmarbitoco.Core.DTO.TokenDTO;
import org.whilmarbitoco.Core.DTO.VerifyEmailDTO;
import org.whilmarbitoco.Service.AuthService;

import java.util.Map;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

    @Inject
    AuthService service;

    @POST
    @Path("/signup")
    public Response signup(EmployeeDTO dto) {
        service.signup(dto);
        return Response.status(Response.Status.CREATED)
                .entity(Map.of("result", "Account Created. Please check your email to verify your account."))
                .build();
    }

    @POST
    @Path("/login")
    public TokenDTO login(LoginDTO dto) {
        return service.authenticate(dto);
    }

    @POST
    @Path("/verify-email")
    public Response verifyEmail(VerifyEmailDTO dto) {
        service.verifyEmail(dto);
        return Response.status(Response.Status.ACCEPTED)
                .entity(Map.of("result", "Email Verified."))
                .build();
    }

    @POST
    @Path("/generate-verification")
    public Response generateCode(LoginDTO dto) {
        service.generateVerification(dto);
        return Response.status(Response.Status.CREATED)
                .entity(Map.of("result", "Please check your email to verify your account."))
                .build();
    }

}
