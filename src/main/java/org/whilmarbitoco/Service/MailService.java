package org.whilmarbitoco.Service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.whilmarbitoco.Core.Model.Employee;

import java.util.List;

@ApplicationScoped
public class MailService {

    @Inject
    Mailer mailer;
    @Inject
    EmployeeService employeeService;

    public void sendEmail(String to, String subject, String body) {
        mailer.send(Mail.withText(to, subject, body));
    }

    public boolean validate(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public void notifyManager(String ingredient, String body) {
        String subject = "DishFlow Notice";
        List<Employee> managers = employeeService.getManagers();

        for (Employee m : managers) {
            sendEmail(m.getUser().getEmail(), subject, body);
        }

    }
}
