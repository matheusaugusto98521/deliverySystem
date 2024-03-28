package com.example.deliverySystem.controller;

import com.example.deliverySystem.entitys.Order;
import com.example.deliverySystem.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("orders")
@CrossOrigin("*")
public class OrderController {

    private OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    @PostMapping("/create-order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order newOrder = this.service.createOrder(order);

        return ResponseEntity.ok().body(newOrder);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> displayAllOrders(){
        List<Order> orderList = this.service.displayOrder();
        return ResponseEntity.ok().body(orderList);
    }

    @PostMapping("/{idOrder}/add-item-to-order/{idItem}")
    public ResponseEntity<?> addItemToOrderExists(@PathVariable Long idOrder, @PathVariable Long idItem){
        try{
            Order orderUpdated = this.service.addItemToListExists(idOrder, idItem);
            return ResponseEntity.ok().body(orderUpdated);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/{idOrder}/total")
    public ResponseEntity<?> calculateOrder(@PathVariable Long idOrder){
        try{
            BigDecimal total = this.service.calculateOrder(idOrder);
            return ResponseEntity.ok().body(total);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/alterate-order")
    public ResponseEntity<?> alterateOrder(@PathVariable Long id, Order updated){
        try{
            Order order = this.service.alterateOrder(id, updated);
            return ResponseEntity.ok().body(order);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        }
    }



}
