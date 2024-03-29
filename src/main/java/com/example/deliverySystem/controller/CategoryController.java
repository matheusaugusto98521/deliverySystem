package com.example.deliverySystem.controller;


import com.example.deliverySystem.DTO.CategoryDTO;
import com.example.deliverySystem.customExceptions.CategoryNotFoundException;
import com.example.deliverySystem.entitys.Category;
import com.example.deliverySystem.entitys.Product;
import com.example.deliverySystem.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@CrossOrigin("*")
public class CategoryController {


    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO){
        Category newCategory = this.categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> displayAllCategories(){
        List<Category> categories = this.categoryService.displayAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @PutMapping("/alterate/{id}")
    public ResponseEntity<?> alterateCategory(@PathVariable long id, @RequestBody CategoryDTO categoryDTO){
        try{
            Category category = this.categoryService.alterateCategory(id, categoryDTO);
            return ResponseEntity.ok().body(category);
        }catch (CategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar categoria: "
            + e.getMessage());
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity<?> getCategoryById(@PathVariable long id){
        try {
            Category getCat = this.categoryService.getCategoryById(id);
            return ResponseEntity.status(HttpStatus.OK).body(getCat);
        }catch (CategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERRO: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id){
        try{
            this.categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        }catch(CategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar categoria: " +
                    e.getMessage());
        }
    }

    @GetMapping("/{idCategory}/products-by-category")
    public ResponseEntity<?> getProdsByCategory(@PathVariable Long idCategory){
        try{
            List<Product> products = this.categoryService.getProductsByCategory(idCategory);
            return ResponseEntity.ok().body(products);
        }catch (CategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERRO: " + e.getMessage());
        }
    }

}
