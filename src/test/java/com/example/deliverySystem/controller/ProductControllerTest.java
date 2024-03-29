package com.example.deliverySystem.controller;

import com.example.deliverySystem.DTO.ProductDTO;
import com.example.deliverySystem.entitys.Product;
import com.example.deliverySystem.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ProductControllerTest {
    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        ProductDTO productDTO = new ProductDTO("Fame 1", BigDecimal.valueOf(28.5));
        Product outputProduct = new Product();

        when(productService.createProduct(productDTO)).thenReturn(outputProduct);
        ResponseEntity<?> responseEntity = productController.createProduct(productDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(outputProduct, responseEntity.getBody());

        verify(productService, times(1)).createProduct(productDTO);

    }
}