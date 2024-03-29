package com.example.deliverySystem.controller;

import com.example.deliverySystem.DTO.ProductDTO;
import com.example.deliverySystem.customExceptions.CategoryNotFoundException;
import com.example.deliverySystem.entitys.Product;
import com.example.deliverySystem.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {


    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/create/")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productData){
        Product newProduct = this.productService.createProduct(productData);
        return ResponseEntity.ok().body(newProduct);
    }

    @PutMapping("/alterate-{prodId}")
    public ResponseEntity<?> alterateProduct(@PathVariable long prodId, @RequestBody Product alteratedProd){
        try{
            Product updateProd = productService.alterateProduct(prodId, alteratedProd);
            return ResponseEntity.status(HttpStatus.OK).body(updateProd);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao alterar produto: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-{prodId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long prodId){
        try{
            productService.deleteProduct(prodId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao deletar produto: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> displayProducts(){
        List<Product> allProd= productService.displayProducts();
        return ResponseEntity.status(HttpStatus.OK).body(allProd);
    }

    @GetMapping("/searchById-{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id){
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }catch(NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao encontrar produto solicitado: " + e.getMessage());
        }
    }

    @GetMapping("/search-")
    public ResponseEntity<?> searchByName(@RequestParam("name") String name){
        try{
            Product result = productService.searchByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERRO: " + e.getMessage());
        }
    }

    @PostMapping("/{idPro}/assign-category/{idCategory}")
    public ResponseEntity<?> assignCategory(@PathVariable Long idPro, @PathVariable Long idCategory){
        try{
            Product product = this.productService.assignCategory(idPro, idCategory);
            return ResponseEntity.ok().body(product);
        }catch (CategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERRO: " + e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERRO: " + e.getMessage());
        }
    }


}
