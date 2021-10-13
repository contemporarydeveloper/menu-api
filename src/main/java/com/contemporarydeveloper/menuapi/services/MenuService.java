package com.contemporarydeveloper.menuapi.services;

import com.contemporarydeveloper.menuapi.models.Menu;
import com.contemporarydeveloper.menuapi.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepo;

    public Menu findMenuById(String menuId) {
        Optional<Menu> menu;
        menu = menuRepo.findById(menuId);

        if(!menu.isPresent()) {
            return null;
        }

        return menu.get();
    }

    public Menu createMenu(Menu menu) {
        return menuRepo.insert(menu);
    }

    public Menu updateMenuById(String menuId, Menu menu) {
        Optional<Menu> storedMenu = menuRepo.findById(menuId);
        if(storedMenu.isEmpty()) {
            return null;
        }

        storedMenu.get().setName(menu.getName());

        return menuRepo.save(storedMenu.get());
    }

    public boolean deleteMenuById(String menuId) {
        if(menuRepo.existsById(menuId)){
            menuRepo.deleteById(menuId);
            return true;
        }
        return false;
    }
}
