package com.contemporarydeveloper.menuapi.controllers;

import com.contemporarydeveloper.menuapi.services.MenuService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MenuControllerTest {
    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;
}
