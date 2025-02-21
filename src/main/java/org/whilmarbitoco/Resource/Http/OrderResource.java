package org.whilmarbitoco.Resource.Http;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.OrderDetailDTO;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.OrderService;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;


    @POST
    @Path("/add")
    public Response create(OrderDetailDTO dto) {
        orderService.createOrder(dto.tableNumber, dto.waiterID, dto.notes, dto.orders);
        return Status.ok("Order Created.");
    }

}
