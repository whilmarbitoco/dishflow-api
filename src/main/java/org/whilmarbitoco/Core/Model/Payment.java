package org.whilmarbitoco.Core.Model;

import jakarta.persistence.*;
import org.whilmarbitoco.Core.utils.PaymentMethod;

import java.time.LocalDate;


@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount")
    private double total;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod payment;

    @Column(name = "paid_amount")
    private double paid;

    @Column(name = "change_amount")
    private double change;

    @Column(name = "created_at")
    private LocalDate created_at;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderDetails order;

    public Payment() {}

    public Payment(double total, PaymentMethod payment, double paid, double change, OrderDetails order) {
        this.total = total;
        this.payment = payment;
        this.paid = paid;
        this.change = change;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public PaymentMethod getPayment() {
        return payment;
    }

    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public OrderDetails getOrder() {
        return order;
    }

    public void setOrder(OrderDetails order) {
        this.order = order;
    }

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDate.now();
    }
}
