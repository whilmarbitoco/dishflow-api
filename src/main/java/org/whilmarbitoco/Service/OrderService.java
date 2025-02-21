package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.whilmarbitoco.Core.DTO.OrderDTO;
import org.whilmarbitoco.Core.Model.*;
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

    @Transactional
    public void createOrder(int tableNumber,Long waiterID, String notes, List<OrderDTO> orders) {
        Tables tbl = tableService.getAvailableByNumber(tableNumber);
        Employee waiter = employeeService.getWaiterById(waiterID);

        OrderDetails orderDetails = new OrderDetails(notes, waiter, tbl);
        List<Order> orderList = new ArrayList<>();

        for (OrderDTO o : orders) {
            Menu menu = menuService.getMenu(o.menuID);
            Order tmp = new Order(menu, orderDetails, o.quantity);
            orderList.add(tmp);
        }

        orderDetails.setOrders(orderList);
        orderDetailRepository.persist(orderDetails);
        tableService.updateTable(tableNumber, TableStatus.Occupied);
    }
}
