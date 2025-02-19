package org.whilmarbitoco.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.whilmarbitoco.Core.Model.Employee;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {
}
