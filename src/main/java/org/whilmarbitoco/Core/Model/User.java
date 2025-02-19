package org.whilmarbitoco.Core.Model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDate created_at;

    @Column(name = "updated_at")
    private LocalDate updated_at;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Employee employee;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;


    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }


    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public Employee getEmployee() {
        return employee;
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

    public Long getId() {
        return id;
    }

}
