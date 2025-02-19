package org.whilmarbitoco.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.DTO.TableDTO;
import org.whilmarbitoco.Core.Model.Tables;
import org.whilmarbitoco.Repository.TableRepository;

import java.util.List;

@ApplicationScoped
public class TableService {

    @Inject
    TableRepository tableRepository;

    @Transactional
    public void addTable(TableDTO dto) {

        if (tableRepository.findByTableNumber(dto.number) != null) {
            throw new BadRequestException("Table number already exist.");
        }

        Tables tb = new Tables(dto.number, dto.capacity);
        tableRepository.persist(tb);
    }

    public List<TableDTO> getTables() {
        return tableRepository.listAll().stream().map(tables -> {
            return new TableDTO(tables.getTable_number(), tables.getStatus(), tables.getCapacity());
        }).toList();
    }

    public void updateTable(TableDTO dto) {
        Tables table = tableRepository.findByTableNumber(dto.number);
        if (table == null) {
            throw new BadRequestException("Table Number Not Found.");
        }

        if (table.getStatus() == dto.status) {
            throw new BadRequestException("Table is already " + table.getStatus());
        }

        tableRepository.updateStatus(table.getId(), dto.status);
    }
}

