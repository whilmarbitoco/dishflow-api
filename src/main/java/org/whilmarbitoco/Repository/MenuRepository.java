package org.whilmarbitoco.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.whilmarbitoco.Core.Model.Menu;

@ApplicationScoped
public class MenuRepository implements PanacheRepository<Menu> {

    public Menu findByName(String name) {
        return find("name", name).firstResult();
    }
}
