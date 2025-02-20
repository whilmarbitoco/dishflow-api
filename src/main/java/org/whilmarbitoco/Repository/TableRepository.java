package org.whilmarbitoco.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.Model.Tables;
import org.whilmarbitoco.Core.utils.TableStatus;

@ApplicationScoped
public class TableRepository implements PanacheRepository<Tables> {

    public Tables findByTableNumber(int number) {
        return find("table_number", number).firstResult();
    }

    @Transactional
    public void updateStatus(Long id, TableStatus status) {
        Tables table = findById(id);

        if (table == null) {
            throw new BadRequestException("Error while updating table status");
        }
        table.setStatus(status);
    }
}
