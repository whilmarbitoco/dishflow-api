package org.whilmarbitoco.Service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.Model.OrderDetails;
import org.whilmarbitoco.Core.Model.Payment;
import org.whilmarbitoco.Core.utils.PaymentMethod;
import org.whilmarbitoco.Repository.PaymentRepository;

@ApplicationScoped
public class PaymentService {

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    OrderService orderService;

    @Transactional
    public Payment pay(Long orderID, double amount, PaymentMethod method) {
        OrderDetails order = orderService.getOrder(orderID);
        if (order.isPaid()) {
            throw new BadRequestException("Order is Already Paid.");
        }

        double total = orderService.calculatePayment(orderID);
        if (amount < total) {
            throw new BadRequestException("Invalid Payment Amount.");
        }

        double change = (amount - total) > 0 ? (amount - total) : 0.0;
        Payment payment = new Payment(total, method, amount, change, order);

        paymentRepository.persist(payment);
        order.pay();

        return payment;
    }
}
