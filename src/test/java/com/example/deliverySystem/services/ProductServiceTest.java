package com.example.deliverySystem.services;

import com.example.deliverySystem.DTO.CategoryDTO;
import com.example.deliverySystem.DTO.ProductDTO;
import com.example.deliverySystem.customExceptions.CategoryNotFoundException;
import com.example.deliverySystem.entitys.Category;
import com.example.deliverySystem.entitys.Product;
import com.example.deliverySystem.repository.ICategoryRepository;
import com.example.deliverySystem.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    IProductRepository productRepository;

    @Mock
    ICategoryRepository categoryRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        MultipartFile image = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
        ProductDTO productDTO = new ProductDTO("Fame 1", BigDecimal.valueOf(28.5));
        Product inputProduct = new Product(productDTO);


        when(productRepository.save(any(Product.class))).thenReturn(inputProduct);

        Product createdProduct = productService.createProduct(productDTO, image);

        assertNotNull(createdProduct);
        assertEquals(inputProduct.getDescription(), createdProduct.getDescription());
        assertEquals(inputProduct.getPrice(), createdProduct.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testAlterateProduct(){
        long prodId = 1;
        Product prodExists = new Product();
        prodExists.setId(prodId);
        prodExists.setDescription("Produto 1");
        prodExists.setPrice(BigDecimal.valueOf(45.5));

        Product updatedProd = new Product();
        updatedProd.setId(prodId);
        updatedProd.setDescription("Produto 1.5");
        updatedProd.setPrice(BigDecimal.valueOf(50.0));

        when(productRepository.findById(prodId)).thenReturn(prodExists);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProd);

        Product result = productService.alterateProduct(prodId, updatedProd);

        assertEquals(updatedProd.getId(), result.getId());
        assertEquals(updatedProd.getDescription(), result.getDescription());
        assertEquals(updatedProd.getPrice(), result.getPrice());

        verify(productRepository, times(1)).findById(prodId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct(){
        long prodId = 1;
        Product prodExists = new Product();
        prodExists.setId(prodId);
        prodExists.setDescription("Fame");
        prodExists.setPrice(BigDecimal.valueOf(50.5));

        when(productRepository.findById(prodId)).thenReturn(prodExists);

        productService.deleteProduct(prodId);

        verify(productRepository, times(1)).deleteById(prodId);
    }

    @Test
    void testDisplayProducts(){
        Product prod1 = new Product();
        Product prod2 = new Product();
        prod1.setId(1);
        prod2.setId(2);
        prod1.setDescription("Prod1");
        prod2.setDescription("Prod2");
        prod1.setPrice(BigDecimal.valueOf(50.5));
        prod2.setPrice(BigDecimal.valueOf(45.5));
        prod1.setCategory(null);
        prod2.setCategory(null);

        List<Product> prodList = Arrays.asList(
            prod1, prod2
        );

        when(productRepository.findAll()).thenReturn(prodList);

        List<Product> result = productService.displayProducts();
        verify(productRepository, times(1)).findAll();

        assertEquals(prodList, result);
    }

    @Test
    void testGetProductById(){
        long prodId = 1L;
        Product product = new Product();
        product.setId(prodId);
        product.setDescription("Fame");
        product.setPrice(BigDecimal.valueOf(5.5));

        when(productRepository.findById(prodId)).thenReturn(product);
        Product result = productService.getProductById(prodId);

        assertEquals(product, result);
        verify(productRepository, times(1)).findById(prodId);
    }

    @Test
    void testSearcByName(){
        long prodId =1L;
        Product product = new Product();
        product.setId(prodId);
        product.setDescription("Fame");
        product.setPrice(BigDecimal.valueOf(28.2));

        when(productRepository.findByDescription(product.getDescription())).thenReturn(product);
        Product result = productService.searchByName(product.getDescription());

        assertEquals(product, result);
        verify(productRepository, times(1)).findByDescription(product.getDescription());
    }

    @Test
    void testAssignCategory(){
        Product product = new Product();
        Category category = new Category();
        Long prodId = 1L;
        Long categoryId = 2L;

        when(productRepository.findById(prodId)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.assignCategory(prodId, categoryId);

        assertEquals(category, result.getCategory());
        verify(productRepository, times(1)).save(product);
    }
}