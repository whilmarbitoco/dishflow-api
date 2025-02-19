package org.whilmarbitoco.Core.Model;

import jakarta.persistence.*;
import org.whilmarbitoco.Core.TableStatus;

import java.time.LocalDate;

@Entity
@Table(name = "`Table`")
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number")
    private int table_number;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TableStatus status;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "created_at")
    private LocalDate created_at;

    @Column(name = "updated_at")
    private LocalDate updated_at;


    public Tables() {}

    public Tables(int table_number, int capacity) {
        this.table_number = table_number;
        this.status = TableStatus.Available;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public int getTable_number() {
        return table_number;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
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
