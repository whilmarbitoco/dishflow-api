package org.whilmarbitoco.Core.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Role")
public class Role {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;


    public Role() {}

    public Long getId() {
        return id;
    }

    public String getRole() {
        return name;
    }
}
