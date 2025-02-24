package org.whilmarbitoco.Resource.Controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.OrderDetailDTO;
import org.whilmarbitoco.Core.DTO.PaymentDTO;
import org.whilmarbitoco.Core.DTO.OrderUpdateDTO;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.OrderService;


@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    @Inject
    OrderService orderService;

    @POST
    @Path("/create")
    public Response create(@Valid OrderDetailDTO dto) {
        orderService.createOrder(dto.tableNumber, dto.waiterID, dto.notes, dto.orders);
        return Status.ok("Order Created.");
    }

    @POST
    @Path("/update")
    public Response update(@Valid OrderUpdateDTO dto) {
        orderService.updateOrderDetail(dto.orderID, dto.status);
        return Status.ok("Order Updated.");
    }

    @POST
    @Path("/pay")
    public PaymentDTO pay(PaymentDTO dto) {
        return orderService.pay(dto.orderID, dto.amount, dto.method);
    }
}
