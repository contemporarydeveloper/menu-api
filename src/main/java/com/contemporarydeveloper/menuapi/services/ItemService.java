package com.contemporarydeveloper.menuapi.services;

import com.contemporarydeveloper.menuapi.models.Item;
import com.contemporarydeveloper.menuapi.models.Menu;
import com.contemporarydeveloper.menuapi.repositories.ItemRepository;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private MenuService menuService;

    public List<Item> getAllItemsInMenu(String menuId) {
        Menu menu = menuService.findMenuById(menuId);
        if(menu == null) {
            return Collections.emptyList();
        }

        List<Item> items = itemRepo.findByMenuId(menuId);
        if(items == null) {
            return Collections.emptyList();
        }

        return items;
    }

    public Item getItemById(String itemId) {
        Optional<Item> item;
        item = itemRepo.findById(itemId);

        if(!item.isPresent()) {
            return null;
        }

        return item.get();
    }

    public Item createItem(String menuId, Item item) throws DuplicateKeyException {
        Menu menu = menuService.findMenuById(menuId);
        if(menu == null) {
            return null;
        }

        item.setMenuId(menuId);
        Item createdItem;
        try {
            createdItem = itemRepo.insert(item);
        } catch (DuplicateKeyException dke) {
            throw dke;
        }
        return createdItem;
    }

    public void updateItemById(String itemId, Item item) {
        itemRepo.insertOrUpdate(itemId, item);
    }

    public boolean deleteItemById(String itemId) {
        if(itemRepo.existsById(itemId)) {
            itemRepo.deleteById(itemId);
            return true;
        }

        return false;
    }
}
