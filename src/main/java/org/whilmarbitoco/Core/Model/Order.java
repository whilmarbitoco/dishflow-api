package org.whilmarbitoco.Core.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_details_id", nullable = false)
    private OrderDetails orderDetails;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;


    public Order() {}

    public Order(Menu menu, OrderDetails orderDetails, int quantity) {
        this.menu = menu;
        this.orderDetails = orderDetails;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
