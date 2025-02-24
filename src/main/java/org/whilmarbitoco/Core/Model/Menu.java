package org.whilmarbitoco.Core.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "is_available")
    private boolean is_available = false;

    @Column(name = "created_at")
    private LocalDate created_at;

    @Column(name = "updated_at")
    private LocalDate updated_at;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuIngredient> ingredients;


    public Menu() {}

    public Menu(String name, double price, String description, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public List<MenuIngredient> getIngredients() {
        return ingredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIngredients(List<MenuIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isAvailable() {
        return is_available ;
    }

    public void setAvailable(boolean available) {
        this.is_available  = available;
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
}
