package com.example.deliverySystem.services;

import com.example.deliverySystem.DTO.OrderItemDTO;
import com.example.deliverySystem.entitys.OrderItems;
import com.example.deliverySystem.repository.IOrderItemsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class OrderItemService {
    private IOrderItemsRepository repository;

    public OrderItemService(IOrderItemsRepository repository){
        this.repository = repository;
    }

    public OrderItems createOrderItem(@RequestBody OrderItemDTO data){
        OrderItems orderItem = new OrderItems(data);
        return this.repository.save(orderItem);
    }

    public void deleteItem(Long idItem){
        OrderItems item = this.repository.findById(idItem).orElseThrow(
                () -> new RuntimeException("Item não encontrado"));

        this.repository.delete(item);
    }

    public OrderItems alterateQuantity(Long idItem, OrderItems itemUpdated){
        OrderItems item = this.repository.findById(idItem).orElseThrow(
                () -> new RuntimeException("Item não encontrado"));

        item.setQuantity(itemUpdated.getQuantity());

        return this.repository.save(item);
    }

}
