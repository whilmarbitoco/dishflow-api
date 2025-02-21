package org.whilmarbitoco.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.whilmarbitoco.Core.Model.OrderDetails;


@ApplicationScoped
public class OrderDetailRepository implements PanacheRepository<OrderDetails> {
}
