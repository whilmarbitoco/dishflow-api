package org.whilmarbitoco.Core.DTO;

import org.whilmarbitoco.Core.utils.PaymentMethod;

public class PaymentDTO {

    public Long orderID;
    public double amount;
    public PaymentMethod method;
    public double change;

}
