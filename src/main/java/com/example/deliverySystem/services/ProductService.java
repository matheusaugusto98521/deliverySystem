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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private IProductRepository productRepository;
    private ICategoryRepository categoryRepository;

    public ProductService(IProductRepository productRepository, ICategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    public Product createProduct(@RequestBody ProductDTO productData, MultipartFile image){
        String imageName = saveImageLocalDir(image);
        Product newproduct = new Product(productData);
        newproduct.setImage("/images/" + imageName);
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
            prodExists.setImage(alteratedProd.getImage());

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

    public Product getProductById(long id){
        Product prodExists = productRepository.findById(id);

        if (prodExists != null){
            return prodExists;
        }else{
            throw new NoSuchElementException("Produto não encontrdo");
        }
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
        Product product = this.productRepository.findById(productId).orElseThrow(() ->
                new RuntimeException("Produto não encontrado"));

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() ->
                new CategoryNotFoundException("Categoria não encontrada"));

        category.setProduct();
    }

    public String saveImageLocalDir(MultipartFile image){
        String imageURL = UUID.randomUUID().toString() + ".png";
        try {
            byte[] imageBytes = image.getBytes();
            Path imagePath = Paths.get("src\\main\\resources\\static\\images\\" + imageURL);

            Files.write(imagePath, imageBytes);
        }catch (IOException e){
            System.out.println("Não foi possível obter os bytes do parâmetro passado");
        }

        return imageURL;
    }


}
