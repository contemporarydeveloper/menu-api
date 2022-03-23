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

import java.net.URI;

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

    @Test
    @DisplayName("Unsuccessful create menu - null menu response from service")
    void createMenuNullMenu() {
        when(menuService.createMenu(menu)).thenReturn(null);

        ResponseEntity response = menuController.createMenu(menu);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Unsuccessful create menu - empty menu response from service")
    void createMenuEmptyMenu() {
        menu.setId("");
        when(menuService.createMenu(menu)).thenReturn(menu);

        ResponseEntity response = menuController.createMenu(menu);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Successful create menu")
    void createMenuSuccessful() {
        when(menuService.createMenu(menu)).thenReturn(menu);

        ResponseEntity response = menuController.createMenu(menu);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(URI.create("/menus/1234"), response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Unsuccessful update menu - not found")
    void updateMenuNotFound() {
        when(menuService.updateMenuById(MENU_ID, menu)).thenReturn(null);

        ResponseEntity response = menuController.updateMenu(MENU_ID, menu);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Successful update menu")
    void updateMenuSuccessful() {
        when(menuService.updateMenuById(MENU_ID, menu)).thenReturn(menu);

        ResponseEntity response = menuController.updateMenu(MENU_ID, menu);

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
