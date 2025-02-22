package org.whilmarbitoco.Resource.Http;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.whilmarbitoco.Core.DTO.OrderDetailDTO;
import org.whilmarbitoco.Core.DTO.PaymentDTO;
import org.whilmarbitoco.Core.DTO.UpdateOrderDTO;
import org.whilmarbitoco.Core.Model.Employee;
import org.whilmarbitoco.Core.utils.Status;
import org.whilmarbitoco.Service.EmployeeService;
import org.whilmarbitoco.Service.OrderService;

import java.util.List;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @POST
    @Path("/create")
    public Response create(OrderDetailDTO dto) {
        orderService.createOrder(dto.tableNumber, dto.waiterID, dto.notes, dto.orders);
        return Status.ok("Order Created.");
    }

    @POST
    @Path("/update")
    public Response update(@Valid UpdateOrderDTO dto) {
        orderService.updateOrderDetail(dto.orderID, dto.status);
        return Status.ok("Order Updated.");
    }

    @POST
    @Path("/pay")
    public PaymentDTO pay(PaymentDTO dto) {
        return orderService.pay(dto.orderID, dto.amount, dto.method);
    }
}
