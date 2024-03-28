package com.example.deliverySystem.controller;

import com.example.deliverySystem.DTO.CategoryDTO;
import com.example.deliverySystem.entitys.Category;
import com.example.deliverySystem.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        CategoryDTO inputCategory = new CategoryDTO("Fame");
        Category outputCategory = new Category();

        when(categoryService.createCategory(inputCategory)).thenReturn(outputCategory);
        ResponseEntity<?> responseEntity = categoryController.createCategory(inputCategory);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(outputCategory, responseEntity.getBody());

        verify(categoryService, times(1)).createCategory(inputCategory);
    }
}