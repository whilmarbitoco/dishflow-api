package org.whilmarbitoco.Resource.Http;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.EmployeeUpdateDTO;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.ImageService;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/employee")
public class EmployeeResource {

    @Inject
    ImageService imageService;

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    @Path("/update")
    public Response update(EmployeeUpdateDTO update) {
        imageService.validate(update.image);
        imageService.saveFile(update.image);
        return Status.ok("Ok");
    }


}
