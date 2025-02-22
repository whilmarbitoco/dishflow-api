package org.whilmarbitoco.Core.DTO;

import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class EmployeeUpdateDTO {

    @FormParam("image")
    public FileUpload image;
}
