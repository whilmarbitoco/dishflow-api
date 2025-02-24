package org.whilmarbitoco;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.whilmarbitoco.Repository.MenuRepository;

@ApplicationScoped
public class Main {


    @Inject
    static MenuRepository rp ;//= new MenuRepository();

    public static void main(String[] args) {

        rp.updateAvailability(Integer.toUnsignedLong(1), false);
    }
}
