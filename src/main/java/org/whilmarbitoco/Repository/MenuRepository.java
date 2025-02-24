package org.whilmarbitoco.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.whilmarbitoco.Core.Model.Menu;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class MenuRepository implements PanacheRepository<Menu> {

    public Menu findByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Menu> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return find("id IN ?1", ids).list();
    }


    @Transactional
    public void updateAvailability(Long menuId, boolean available) {
        update("is_available = ?1 WHERE id = ?2", available, menuId);
    }
}
