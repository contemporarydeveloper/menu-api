package com.contemporarydeveloper.menuapi.controllers;

import com.contemporarydeveloper.menuapi.models.Menu;
import com.contemporarydeveloper.menuapi.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/{menu-id}")
    public ResponseEntity getMenuById(@PathVariable("menu-id") String menuId) {
        Menu menu = menuService.findMenuById(menuId);
        if(menu == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(menu);
    }

    @PostMapping
    public ResponseEntity createMenu(@Valid @RequestBody Menu menu) {
        Menu insertedMenu = menuService.createMenu(menu);
        if(insertedMenu == null || insertedMenu.getId().equals("")) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.created(URI.create("/menus/" + insertedMenu.getId())).build();
    }

    @PutMapping("/{menu-id}")
    public ResponseEntity updateMenu(@PathVariable("menu-id") String menuId, @Valid @RequestBody Menu menu) {
        Menu updatedMenu = menuService.updateMenuById(menuId, menu);
        if(updatedMenu == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/{menu-id}")
    public ResponseEntity deleteMenu(@PathVariable("menu-id") String menuId) {
        if(!menuService.deleteMenuById(menuId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
