package com.contemporarydeveloper.menuapi.controllers;

import com.contemporarydeveloper.menuapi.models.Item;
import com.contemporarydeveloper.menuapi.services.ItemService;
import com.mongodb.DuplicateKeyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    private static final String ITEM_ID = "1234";
    private static final String ITEM_NAME = "burger";
    private static final String ITEM_PRICE = "4.99";

    private static final String MENU_ID = "5678";

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @Test
    @DisplayName("Unsuccessful get all items for menu - not found")
    void getAllItemsNotFound() {
        when(itemService.getAllItemsInMenu(MENU_ID)).thenReturn(Collections.emptyList());

        ResponseEntity response = itemController.getAllItems(MENU_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Successful get all items for menu")
    void getAllItemsSuccessful() {
        Item item = generateItem();
        List<Item> items = List.of(item);
        when(itemService.getAllItemsInMenu(MENU_ID)).thenReturn(items);

        ResponseEntity response = itemController.getAllItems(MENU_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(items, response.getBody());
    }

    @Test
    @DisplayName("Unsuccessful get item by id - not found")
    void getItemByIdNotFound() {
        when(itemService.getItemById(ITEM_ID)).thenReturn(null);

        ResponseEntity response = itemController.getItemById(ITEM_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Successful get item by id")
    void getItemByIdSuccessful() {
        Item item = generateItem();
        when(itemService.getItemById(ITEM_ID)).thenReturn(item);

        ResponseEntity response = itemController.getItemById(ITEM_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(item, response.getBody());
    }

    @Test
    @DisplayName("Unsuccessful create item - null item response from service")
    void createItemNullItem() {
        Item item = generateItem();
        when(itemService.createItem(MENU_ID, item)).thenReturn(null);

        ResponseEntity response = itemController.createItem(MENU_ID, item);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Unsuccessful create item - empty item response from service")
    void createItemEmptyItem() {
        Item item = generateItem();
        item.setId("");
        when(itemService.createItem(MENU_ID, item)).thenReturn(item);

        ResponseEntity response = itemController.createItem(MENU_ID, item);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Unsuccessful create item - duplicate key exception from service")
    void createItemNullMenu() {
        Item item = generateItem();
        when(itemService.createItem(MENU_ID, item)).thenThrow(DuplicateKeyException.class);

        ResponseEntity response = itemController.createItem(MENU_ID, item);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("An item with that ID exists - please try again", response.getBody());
        assertNull(response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Successful create item")
    void createItemSuccessful() {
        Item item = generateItem();
        when(itemService.createItem(MENU_ID, item)).thenReturn(item);

        ResponseEntity response = itemController.createItem(MENU_ID, item);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(URI.create("/menus/" + MENU_ID + "/items/" + ITEM_ID), response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Successful update item")
    void updateItemSuccessful() {
        Item item = generateItem();

        ResponseEntity response = itemController.updateItem(ITEM_ID, item);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Unsuccessful delete item - not found")
    void deleteItemNotFound() {
        when(itemService.deleteItemById(ITEM_ID)).thenReturn(false);

        ResponseEntity response = itemController.deleteItem(ITEM_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Unsuccessful delete item - not found")
    void deleteItemSuccessful() {
        when(itemService.deleteItemById(ITEM_ID)).thenReturn(true);

        ResponseEntity response = itemController.deleteItem(ITEM_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private Item generateItem() {
        Item item = new Item();
        item.setId(ITEM_ID);
        item.setName(ITEM_NAME);
        item.setPrice(ITEM_PRICE);

        return item;
    }
}
