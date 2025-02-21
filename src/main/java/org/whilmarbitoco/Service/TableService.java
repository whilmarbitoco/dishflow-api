package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.DTO.TableDTO;
import org.whilmarbitoco.Core.Model.Tables;
import org.whilmarbitoco.Core.utils.TableStatus;
import org.whilmarbitoco.Repository.TableRepository;

import java.util.List;

@ApplicationScoped
public class TableService {

    @Inject
    TableRepository tableRepository;

    @Transactional
    public void addTable(int number, int capacity) {

        if (tableRepository.findByTableNumber(number) != null) {
            throw new BadRequestException("Table number already exist.");
        }

        Tables tb = new Tables(number, capacity);
        tableRepository.persist(tb);
    }

    public List<TableDTO> getAll() {
        return tableRepository.listAll().stream().map(tables -> {
            return new TableDTO(tables.getTable_number(), tables.getStatus(), tables.getCapacity());
        }).toList();
    }

    public void updateTable(int number, TableStatus status) {
        Tables table = tableRepository.findByTableNumber(number);
        if (table == null) {
            throw new BadRequestException("Table Number Not Found.");
        }

        if (table.getStatus() == status) {
            throw new BadRequestException("Table is already " + table.getStatus());
        }

        tableRepository.updateStatus(table.getId(), status);
    }

    public TableStatus getStatus(int tableNumber) {
        Tables tbl = tableRepository.findByTableNumber(tableNumber);

        if (tbl == null) {
            throw new BadRequestException("Table number " + tableNumber + " not found.");
        }

        return tbl.getStatus();
    }

    public Tables getAvailableByNumber(int tableNumber) {
        Tables table = tableRepository.findByTableNumber(tableNumber);
        if (table == null || table.getStatus() != TableStatus.Available) {
            throw new BadRequestException("Table is not available.");
        }

        return table;
    }
}

