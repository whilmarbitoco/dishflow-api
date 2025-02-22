package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.DTO.OrderDTO;
import org.whilmarbitoco.Core.DTO.PaymentDTO;
import org.whilmarbitoco.Core.Model.*;
import org.whilmarbitoco.Core.utils.OrderStatus;
import org.whilmarbitoco.Core.utils.PaymentMethod;
import org.whilmarbitoco.Core.utils.TableStatus;
import org.whilmarbitoco.Repository.*;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderDetailRepository orderDetailRepository;
    @Inject
    TableService tableService;
    @Inject
    EmployeeService employeeService;
    @Inject
    MenuService menuService;
    @Inject
    PaymentService paymentService;
    @Inject
    IngredientService ingredientService;

    @Transactional
    public void createOrder(int tableNumber,Long waiterID, String notes, List<OrderDTO> orders) {
        Tables tbl = tableService.getAvailableByNumber(tableNumber);
        Employee waiter = employeeService.getWaiterById(waiterID);

        ingredientService.validateQuantity(orders);
        OrderDetails orderDetails = new OrderDetails(notes, waiter, tbl);
        List<Order> orderList = new ArrayList<>();

        for (OrderDTO o : orders) {
            Menu menu = menuService.getMenu(o.menuID);
            Order tmp = new Order(menu, orderDetails, o.quantity);
            orderList.add(tmp);
        }

        orderDetails.setOrders(orderList);
        orderDetailRepository.persist(orderDetails);
//        TODO: notify all waiters for table update
        tableService.updateTable(tableNumber, TableStatus.Occupied);
    }

    @Transactional
    public void updateOrderDetail(Long orderID, OrderStatus status) {
        OrderDetails order = orderDetailRepository.findById(orderID);
        if (order == null) {
            throw new BadRequestException("Order Not Found.");
        }

        if (order.getStatus().ordinal() < OrderStatus.Served.ordinal()) {
            throw new BadRequestException("Update Order Invalid. Order is already " + order.getStatus() + ".");
        }

//        TODO: notify all waiters for order update
        order.setStatus(status);
    }

    public OrderDetails getOrder(Long id) {
        return orderDetailRepository.findById(id);
    }

    public double calculatePayment(Long id) {
        OrderDetails orderDetails = getOrder(id);
        if (orderDetails == null) {
            throw new BadRequestException("Payment Failed. Order Invalid.");
        }

        double total = 0.0;
        List<Order> orders = orderDetails.getOrders();
        for (Order o : orders) {
            Menu menu = menuService.getMenu(o.getMenu().getId());
            double price = menu.getPrice();
            double tmpTotal = price * o.getQuantity();
            total += tmpTotal;
        }

        return total;
    }

    public PaymentDTO pay(Long orderID, double amount, PaymentMethod method) {
        Payment payment = paymentService.pay(orderID, amount, method);

        PaymentDTO dto = new PaymentDTO();
        dto.orderID = payment.getId();
        dto.change = payment.getChange();
        dto.amount = payment.getTotal();
        dto.method = payment.getPayment();
        return dto;
    }
}
