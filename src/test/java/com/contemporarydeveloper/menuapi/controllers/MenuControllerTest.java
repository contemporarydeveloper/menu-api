package com.contemporarydeveloper.menuapi.controllers;

import com.contemporarydeveloper.menuapi.models.Menu;
import com.contemporarydeveloper.menuapi.services.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuControllerTest {
    private static final String MENU_ID = "1234";
    private static final String MENU_NAME = "dinner";

    private Menu menu;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    @BeforeEach
    void setUp() {
        menu = generateMenu();
    }

    @Test
    @DisplayName("Unsuccessful get menu by id - not found")
    void getMenuByIdNotFound() {
        when(menuService.findMenuById(MENU_ID)).thenReturn(null);

        ResponseEntity response = menuController.getMenuById(MENU_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Successful get menu by id")
    void getMenuByIdSuccessful() {
        when(menuService.findMenuById(MENU_ID)).thenReturn(menu);

        ResponseEntity response = menuController.getMenuById(MENU_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(menu, response.getBody());
    }

    private Menu generateMenu() {
        Menu menu = new Menu();
        menu.setId(MENU_ID);
        menu.setName(MENU_NAME);
        return menu;
    }
}
