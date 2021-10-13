package com.contemporarydeveloper.menuapi.controllers;

import com.contemporarydeveloper.menuapi.models.Item;
import com.contemporarydeveloper.menuapi.services.ItemService;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/menus/{menu-id}/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity getAllItems(@PathVariable("menu-id") String menuId) {
        List<Item> items = itemService.getAllItemsInMenu(menuId);
        if(items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(items);
    }

    @GetMapping("/{item-id}")
    public ResponseEntity getItemById(@PathVariable("item-id") String itemId) {
        Item item = itemService.getItemById(itemId);
        if(item == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity createItem(@PathVariable("menu-id") String menuId, @Validated @RequestBody Item item) {
        Item createdItem;
        try {
            createdItem = itemService.createItem(menuId, item);
        } catch (DuplicateKeyException dke) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("An item with that ID exists - please try again");
        }
        if(createdItem == null || createdItem.getId().equals("")) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.created(URI.create("/menus/" + menuId + "/items/" + item.getId())).build();
    }

    @PutMapping("/{item-id}")
    public ResponseEntity updateItem(@PathVariable("item-id") String itemId, @Validated @RequestBody Item item) {
        itemService.updateItemById(itemId, item);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{item-id}")
    public ResponseEntity deleteItem(@PathVariable("item-id") String itemId) {
        if(!itemService.deleteItemById(itemId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
