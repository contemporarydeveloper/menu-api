package com.contemporarydeveloper.menuapi.repositories;

import com.contemporarydeveloper.menuapi.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemRepositoryTest {
    private static final String ITEM_ID = "1234";
    private static final String MENU_ID = "5678";
    private static final String ITEM_NAME = "burger";
    private static final String ITEM_PRICE = "4.99";

    private Item item;

    @Spy
    private ItemRepository itemRepo;

    @Captor
    private ArgumentCaptor<Item> itemCaptor;

    @BeforeEach
    void setUp() {
        item = generateItem();
    }

    @Test
    @DisplayName("If item does not exist, insert item")
    void insertItem() {
        when(itemRepo.findById(ITEM_ID)).thenReturn(Optional.empty());

        itemRepo.insertOrUpdate(ITEM_ID, item);

        verify(itemRepo).insert(itemCaptor.capture());
        assertEquals(item, itemCaptor.getValue());
    }

    @Test
    @DisplayName("If item does exist, update item")
    void updateItem() {
        when(itemRepo.findById(ITEM_ID)).thenReturn(Optional.of(item));

        itemRepo.insertOrUpdate(ITEM_ID, item);

        verify(itemRepo).save(itemCaptor.capture());
        assertEquals(item, itemCaptor.getValue());
    }

    private Item generateItem() {
        Item item = new Item();
        item.setId(ITEM_ID);
        item.setMenuId(MENU_ID);
        item.setName(ITEM_NAME);
        item.setPrice(ITEM_PRICE);
        return item;
    }
}
