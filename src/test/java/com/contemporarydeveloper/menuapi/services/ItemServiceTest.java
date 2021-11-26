package com.contemporarydeveloper.menuapi.services;

import com.contemporarydeveloper.menuapi.models.Item;
import com.contemporarydeveloper.menuapi.models.Menu;
import com.contemporarydeveloper.menuapi.repositories.ItemRepository;
import com.mongodb.DuplicateKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    private static final String MENU_ID = "1234";
    private static final String MENU_NAME = "dinner";
    private static final String ITEM_ID = "5678";
    private static final String ITEM_NAME = "burger";
    private static final String ITEM_PRICE = "4.99";

    private Menu menu;
    private Item item;

    @Mock
    private ItemRepository itemRepo;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        menu = generateMenu();
        item = generateItem();
    }

    @Test
    @DisplayName("Unsuccessful create item - duplicate key exception")
    void createItemDuplicateKeyException() throws DuplicateKeyException {
        when(menuService.findMenuById(MENU_ID)).thenReturn(menu);
        when(itemRepo.insert(item)).thenThrow(DuplicateKeyException.class);

        assertThrows(DuplicateKeyException.class, () -> itemService.createItem(MENU_ID, item));
    }

    private Menu generateMenu() {
        Menu menu = new Menu();
        menu.setId(MENU_ID);
        menu.setName(MENU_NAME);
        return menu;
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
