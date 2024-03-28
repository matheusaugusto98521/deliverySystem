package com.example.deliverySystem.services;

import com.example.deliverySystem.entitys.Order;

import com.example.deliverySystem.entitys.OrderItems;
import com.example.deliverySystem.repository.IOrderItemsRepository;
import com.example.deliverySystem.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {


    private IOrderRepository orderRepository;
    private IOrderItemsRepository orderItemsRepository;

    public OrderService(IOrderRepository orderRepository, IOrderItemsRepository orderItemsRepository){
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    public Order createOrder(@RequestBody Order order){
            return orderRepository.save(order);
    }
    //mostrar pedido
    public List<Order> displayOrder(){
        return orderRepository.findAll();
    }

    //calcular total do pedido
    public BigDecimal calculateOrder(Long idOrder){
        Order order = this.orderRepository.findById(idOrder).orElseThrow(
                () -> new RuntimeException("Pedido não encontardo"));

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItems> orderItems = order.getOrderItems();
        for(OrderItems orderItem : orderItems){
            BigDecimal itemTotal = orderItem.getValueItem().multiply(
                    BigDecimal.valueOf(orderItem.getQuantity())
            );
            total = total.add(itemTotal);
        }
        return total;
    }

    public Order addItemToListExists(Long idOrder,Long idOrderItem){
        Order order = this.orderRepository.findById(idOrder).orElseThrow(()
        -> new RuntimeException("Pedido não encontrado"));

        OrderItems orderItem = orderItemsRepository.findById(idOrderItem).orElseThrow(()
        -> new RuntimeException("Item não encontrado"));

        List<OrderItems> itemsExists = order.getOrderItems();

        Optional<OrderItems> existsItem = itemsExists.stream()
                        .filter(item -> item.getId().equals(idOrderItem)).findFirst();
        itemsExists.add(orderItem);

        if(existsItem.isPresent()){
            OrderItems itemUpdate = existsItem.get();
            itemUpdate.setQuantity(itemUpdate.getQuantity() + 1);
        }else{
            itemsExists.add(orderItem);
        }

        order.setOrderItems(itemsExists);

        return this.orderRepository.save(order);
    }

    public Order alterateOrder(Long id, Order orderUpdated){
        Order order = this.orderRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Pedido não encontrado"));

        order.setOrderItems(orderUpdated.getOrderItems());
        return this.orderRepository.save(order);
    }




}
