package org.whilmarbitoco.Core.Model;

import jakarta.persistence.*;
import org.whilmarbitoco.Core.utils.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "OrderDetails")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "notes")
    private String notes;

    @Column(name = "is_paid")
    private boolean is_paid;

    @Column(name = "created_at")
    private LocalDate created_at;

    @Column(name = "updated_at")
    private LocalDate updated_at;

    @OneToMany(mappedBy = "orderDetails", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id")
    private Employee waiter;

    public OrderDetails() {}

    public OrderDetails(OrderStatus status, String notes, Employee waiter, List<Order> orders) {
        this.status = status;
        this.notes = notes;
        this.waiter = waiter;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Employee getWaiter() {
        return waiter;
    }

    public void setWaiter(Employee waiter) {
        this.waiter = waiter;
    }

    public boolean isPaid() {
        return is_paid;
    }

    public void pay() {
        this.is_paid = true;
    }

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDate.now();
        this.updated_at = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = LocalDate.now();
    }

}
