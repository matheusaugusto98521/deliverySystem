package com.example.deliverySystem.services;

import com.example.deliverySystem.DTO.CategoryDTO;
import com.example.deliverySystem.customExceptions.CategoryNotFoundException;
import com.example.deliverySystem.entitys.Category;
import com.example.deliverySystem.entitys.Product;
import com.example.deliverySystem.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryService {

    private ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    public Category createCategory(@RequestBody CategoryDTO categoryDTO){
        Category newCategory = new Category(categoryDTO);
        this.categoryRepository.save(newCategory);
        return newCategory;
    }

    public Category alterateCategory(Long id, @RequestBody CategoryDTO categoryDTO) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = this.categoryRepository.findById(id);

        if(optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            if (!categoryDTO.description().isEmpty()) {
                category.setDescription(categoryDTO.description());
                category = this.categoryRepository.save(category);
            }
            return category;
        }else{
            throw new CategoryNotFoundException("Categoria não encontrada");
        }
    }


    public void deleteCategory(long id) throws CategoryNotFoundException {
        Category category = this.categoryRepository.findById(id);
        if(category != null){
            this.categoryRepository.delete(category);
        }else{
            throw new CategoryNotFoundException("Categoria não encontrada");
        }
    }

    public List<Category> displayAllCategories(){
        return this.categoryRepository.findAll();
    }

    public Category getCategoryById(long id) throws CategoryNotFoundException {
        Category category = this.categoryRepository.findById(id);

        if (category != null){
            return category;
        }else{
            throw new CategoryNotFoundException("Categoria não encontrada");
        }
    }

    public List<Product> getProductsByCategory(Long id) throws CategoryNotFoundException {
        Category category = this.categoryRepository.findById(id).orElseThrow(() ->
                new CategoryNotFoundException("Categoria não encontrada"));

        return category.getProduct();
    }
}
