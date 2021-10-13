package com.contemporarydeveloper.menuapi.repositories;

import com.contemporarydeveloper.menuapi.models.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findByMenuId(String menuId);

    default void insertOrUpdate(String itemId, Item item) {
        Optional<Item> storedItem = findById(itemId);
        if(storedItem.isEmpty()) {
            insert(item);
        } else {
            storedItem.get().setName(item.getName());
            storedItem.get().setPrice(item.getPrice());
            save(storedItem.get());
        }
    }
}
