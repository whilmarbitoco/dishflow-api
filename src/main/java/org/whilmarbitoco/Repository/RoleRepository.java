package org.whilmarbitoco.Repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.whilmarbitoco.Core.Model.Role;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<Role> {

    public Role findByName(String name) {
        return find("name", name).firstResult();
    }
}
