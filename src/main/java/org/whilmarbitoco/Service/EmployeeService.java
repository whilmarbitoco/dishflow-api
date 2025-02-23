package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.whilmarbitoco.Core.DTO.EmployeeDTO;
import org.whilmarbitoco.Core.Model.Employee;
import org.whilmarbitoco.Core.Model.Role;
import org.whilmarbitoco.Core.Model.User;
import org.whilmarbitoco.Repository.EmployeeRepository;
import org.whilmarbitoco.Repository.RoleRepository;

import java.util.List;

@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;
    @Inject
    UserService userService;
    @Inject
    RoleRepository roleRepository;
    @Inject
    ImageService imageService;
    @Inject
    MailService mailService;

    public Employee getWaiter(Long id) {
        Employee waiter = employeeRepository.findById(id);

        if (waiter == null || !waiter.getRole().getRole().equals("Waiter")) {
            throw new BadRequestException("Invalid Waiter ID.");
        }
        return waiter;
    }

    @Transactional
    public void create(String email, String password, String firstname, String lastname, Role role) {
        userService.create(email, password);
        User user = userService.getByEmail(email);

        Employee employee = new Employee(firstname, lastname);
        employee.setUser(user);
        employee.setRole(role);
        employeeRepository.persist(employee);
    }

    public List<Employee> getEmployeeByRole(String employeeRole) {
        Role role = roleRepository.find("name", employeeRole).firstResult();
        if (role == null) {
            throw new InternalServerErrorException("Something went wrong.");
        }
        return employeeRepository.find("role IN ?1", role).list();
    }

    @Transactional
    public void updateEmployee(Long id, String email, String firstname, String lastname, FileUpload image) {
        if (!mailService.validate(email)) {
            throw new BadRequestException("Invalid email format.");
        }
        imageService.validate(image);
        User user = userService.getById(id);
        User checkEmail = userService.getByEmail(email);
        if (checkEmail != null && !user.getEmail().equals(checkEmail.getEmail())) {
            throw new BadRequestException("Email Already registered ");
        }

        Employee employee = getByUser(user);
        if (!user.getEmail().equals(email)) {
            user.setEmail(email);
        }

        String file = imageService.saveFile(image);
        user.setPhoto(file);

        employee.setFirstname(firstname);
        employee.setLastname(lastname);
    }

    public Employee getByUser(User user) {
            Employee employee = employeeRepository.find("user", user).firstResult();
        if (employee == null) {
            throw new BadRequestException("User with email " + user.getEmail() + " is not an employee.");
        }
        return employee;
    }

    public EmployeeDTO getById(Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new BadRequestException("Employee not found.");
        }
        EmployeeDTO dto = new EmployeeDTO();
        dto.id = employee.getId();
        dto.email = employee.getUser().getEmail();
        dto.role = employee.getRole().getRole();
        dto.firstname = employee.getFirstname();
        dto.lastname = employee.getLastname();;
        dto.photo = employee.getUser().getPhoto();
        dto.verified = employee.getUser().IsVerified();
        return dto;
    }


    public List<EmployeeDTO> getAll() {
        return  employeeRepository.listAll().stream()
                .map(employee -> {
                    EmployeeDTO dto = new EmployeeDTO();
                    dto.id = employee.getId();
                    dto.email = employee.getUser().getEmail();
                    dto.role = employee.getRole().getRole();
                    dto.firstname = employee.getFirstname();
                    dto.lastname = employee.getLastname();;
                    dto.photo = employee.getUser().getPhoto();
                    dto.verified = employee.getUser().IsVerified();
                    return dto;
                })
                .toList();
    }
}
