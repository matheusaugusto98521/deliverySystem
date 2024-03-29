package com.example.deliverySystem.services;

import com.example.deliverySystem.DTO.OrderItemDTO;
import com.example.deliverySystem.entitys.Order;
import com.example.deliverySystem.entitys.OrderItems;
import com.example.deliverySystem.entitys.Product;
import com.example.deliverySystem.repository.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    IOrderRepository orderRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testDisplayOrder() {
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);

        List<Order> orderList = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orderList);
        List<Order> result = orderService.displayOrder();

        assertEquals(orderList, result);

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testCalculateOrder() {
        Product product = new Product();
        product.setId(1L);
        product.setDescription("Fame");
        product.setPrice(BigDecimal.valueOf(5.5));

        List<Product> products = Arrays.asList(product);

        OrderItemDTO data = new OrderItemDTO(2, BigDecimal.valueOf(5.5));
        OrderItems item1 = new OrderItems(data);
        OrderItems item2 = new OrderItems(data);

        List<OrderItems> orderItems = Arrays.asList(item1, item2);
        Order order = new Order();
        order.setOrderItems(orderItems);
        Long idOrder = 1L;

        when(orderRepository.findById(idOrder)).thenReturn(Optional.of(order));
        BigDecimal expected = BigDecimal.valueOf(22.0);
        BigDecimal result = this.orderService.calculateOrder(idOrder);

        assertEquals(expected, result);

    }
}