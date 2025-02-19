package org.whilmarbitoco.Resource.Http;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.whilmarbitoco.Repository.UserRepository;



@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/")
public class IndexResource {

    @Inject
    UserRepository repository;

}
