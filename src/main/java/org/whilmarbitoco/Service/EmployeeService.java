package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.Model.Employee;
import org.whilmarbitoco.Core.Model.Role;
import org.whilmarbitoco.Core.Model.User;
import org.whilmarbitoco.Repository.EmployeeRepository;
import org.whilmarbitoco.Repository.UserRepository;

@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    UserRepository userRepository;

    public Employee getWaiterById(Long id) {
        Employee waiter = employeeRepository.findById(id);

        if (waiter == null || !waiter.getRole().getRole().equals("Waiter")) {
            throw new BadRequestException("Invalid Waiter ID.");
        }
        return waiter;
    }


    @Transactional
    public void create(String email, String password, String firstname, String lastname, Role role) {
        User user = new User(email, password);
        userRepository.persist(user);

        Employee employee = new Employee(firstname, lastname);
        employee.setUser(user);
        employee.setRole(role);
        employeeRepository.persist(employee);
    }

}
