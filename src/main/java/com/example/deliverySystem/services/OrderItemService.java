package com.example.deliverySystem.services;

import com.example.deliverySystem.DTO.OrderItemDTO;
import com.example.deliverySystem.entitys.OrderItems;
import com.example.deliverySystem.entitys.Product;
import com.example.deliverySystem.repository.IOrderItemsRepository;
import com.example.deliverySystem.repository.IProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemService {
    private IOrderItemsRepository repository;
    private ProductService productService;

    public OrderItemService(IOrderItemsRepository repository, ProductService productService){
        this.repository = repository;
        this.productService = productService;
    }

    public OrderItems createOrderItem(OrderItemDTO data, Long idProduct){
        OrderItems orderItem = new OrderItems(data);
        Product product = this.productService.getProductById(idProduct);
        orderItem.setProduct(product);
        orderItem.setValueItem();

        return this.repository.save(orderItem);
    }

    public void deleteItem(Long idItem){
        OrderItems item = getById(idItem);

        this.repository.delete(item);
    }

    public OrderItems alterateQuantity(Long idItem, int quantity){
        OrderItems item = getById(idItem);

        item.setQuantity(quantity);
        item.setValueItem();

        return this.repository.save(item);
    }

    public List<OrderItems> getAll(){
        return  this.repository.findAll();
    }

    public OrderItems getById(Long idItem){
        return this.repository.findById(idItem).orElseThrow(
                () -> new RuntimeException("Item não encontrado"));
    }
}
