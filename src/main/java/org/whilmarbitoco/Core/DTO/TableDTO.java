package org.whilmarbitoco.Core.DTO;

import org.whilmarbitoco.Core.TableStatus;

public class TableDTO {

    public int number;
    public TableStatus status;
    public int capacity;

    public TableDTO(int number, TableStatus status, int capacity) {
        this.number = number;
        this.status = status;
        this.capacity = capacity;
    }
}
