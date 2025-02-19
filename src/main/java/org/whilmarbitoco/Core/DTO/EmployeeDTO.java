package org.whilmarbitoco.Core.DTO;

public class EmployeeDTO {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String role;


    public EmployeeDTO(String email, String password, String firstname, String lastname, String role) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
