package com.example.deliverySystem.services;

import com.example.deliverySystem.DTO.ProductDTO;
import com.example.deliverySystem.customExceptions.CategoryNotFoundException;
import com.example.deliverySystem.entitys.Category;
import com.example.deliverySystem.entitys.Product;
import com.example.deliverySystem.repository.ICategoryRepository;
import com.example.deliverySystem.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductService {

    private IProductRepository productRepository;
    private CategoryService categoryService;

    public ProductService(IProductRepository productRepository, CategoryService categoryService){
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }
    public Product createProduct(@RequestBody ProductDTO productData){
        Product newproduct = new Product(productData);
        this.productRepository.save(newproduct);
        return newproduct;
    }

    public Product alterateProduct(long productId, Product alteratedProd){
        Product prodExists = productRepository.findById(productId);

        if(prodExists == null){
            throw  new NoSuchElementException("Produto não encontrado");
        }else{
            prodExists.setDescription(alteratedProd.getDescription());
            prodExists.setPrice(alteratedProd.getPrice());
            prodExists.setCategory(alteratedProd.getCategory());

            return productRepository.save(prodExists);
        }
    }

    public void deleteProduct(long prodId){
        Product prodExists = productRepository.findById(prodId);

        if(prodExists == null){
            throw new NoSuchElementException("Produto não encontrado");
        }else{
            productRepository.deleteById(prodId);
        }
    }

    public List<Product> displayProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return this.productRepository.findById(id).orElseThrow(()
        -> new NoSuchElementException("Produto não encontrado"));
    }

    public Product searchByName(String descriptionProduct){
        Product prodExists = productRepository.findByDescription(descriptionProduct);

        if(prodExists != null){
            return prodExists;
        }else{
            throw new RuntimeException("Produto não encontrado");
        }
    }

    public Product assignCategory(Long productId, Long categoryId) throws CategoryNotFoundException {
        Product product = getProductById(productId);
        Category category = this.categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        List<Product> products = category.getProduct();
        products.add(product);
        category.setProduct(products);

        this.productRepository.save(product);
        this.categoryService.saveCategory(category);

        return product;
    }


}
