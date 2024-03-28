package com.example.deliverySystem.services;

import com.example.deliverySystem.DTO.CategoryDTO;
import com.example.deliverySystem.customExceptions.CategoryNotFoundException;
import com.example.deliverySystem.entitys.Category;
import com.example.deliverySystem.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;

    @Mock
    private ICategoryRepository categoryRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory(){
        CategoryDTO categoryDTO = new CategoryDTO("Fame");
        Category inputCategory = new Category(categoryDTO);


        when(categoryRepository.save(any(Category.class))).thenReturn(inputCategory);

        Category createdCategory = categoryService.createCategory(categoryDTO);

        assertNotNull(createdCategory);
        assertEquals(inputCategory.getDescription(), createdCategory.getDescription());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testAlterateCategory(){
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO("Fame");
        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setDescription("fane 2");
        Category updatedCategory = new Category();
        updatedCategory.setDescription(categoryDTO.description());

        System.out.println("Confirmando se existingCategory esta sendo criado, valor do id -> : " + existingCategory.getId());
        System.out.println("Confirmando se existingCategory esta sendo criado, valor do description -> :" + existingCategory.getDescription());

        // Mock do método findById do repositório
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        try{
            Category alteredCategory = categoryService.alterateCategory(categoryId, categoryDTO);

            // Assert
            assertEquals(updatedCategory.getDescription(), alteredCategory.getDescription());
            assertEquals(existingCategory.getId(), alteredCategory.getId());
        }catch (CategoryNotFoundException e){
            System.out.println("Erro ao atualizar categoria: " + e.getMessage());
        }
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    void testDeleteCategory(){
        long catId = 1L;
        Category category = new Category();
        category.setId(catId);
        category.setDescription("Fame");

        when(categoryRepository.findById(catId)).thenReturn(category);
        try{
            categoryService.deleteCategory(catId);
        }catch (CategoryNotFoundException e){
            System.out.println("Erro ao deletar categoria: " + e.getMessage());
        }
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void testDisplayCategories(){
        Category cat1 = new Category();
        Category cat2 = new Category();
        cat1.setId(1L);
        cat1.setDescription("Fame");
        cat2.setId(2L);
        cat2.setDescription("Fuck Fame");

        List<Category> cateList = Arrays.asList(cat1,cat2);


        when(categoryRepository.findAll()).thenReturn(cateList);
        List<Category> result = categoryService.displayAllCategories();
        verify(categoryRepository, times(1)).findAll();

        assertEquals(cateList, result);
    }

    @Test
    void testGetCategoryById(){
        long catId = 1L;
        Category category = new Category();
        category.setId(catId);
        category.setDescription("Fame");

        when(categoryRepository.findById(catId)).thenReturn(category);

        try {
            Category catExists = categoryService.getCategoryById(catId);
            assertEquals(category, catExists);
        }catch (CategoryNotFoundException e){
            System.out.println("Erro ao procurar categoria: " + e.getMessage());
        }


        verify(categoryRepository, times(1)).findById(catId);

    }
}