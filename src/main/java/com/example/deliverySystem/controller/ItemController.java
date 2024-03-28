package com.example.deliverySystem.controller;

import com.example.deliverySystem.DTO.OrderItemDTO;
import com.example.deliverySystem.entitys.OrderItems;
import com.example.deliverySystem.services.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
@CrossOrigin("*")
public class ItemController {

    private OrderItemService service;

    public ItemController(OrderItemService service){
        this.service = service;
    }

    @PostMapping("/create-item")
    public ResponseEntity<OrderItems> createItem(@RequestBody OrderItemDTO item){
        OrderItems itemCreated = this.service.createOrderItem(item);
        return ResponseEntity.ok().body(itemCreated);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteItem(@PathVariable Long id){
        try{
            this.service.deleteItem(id);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        }

    }

    @PutMapping ("/{id}/alterate-quantity")
    public ResponseEntity<?> alterateQuantity(@PathVariable Long id, OrderItems itemUpdated){
        try{
            OrderItems item = this.service.alterateQuantity(id, itemUpdated);
            return ResponseEntity.ok().body(item);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        }
    }


}
