package org.factoriaf5.ecommerce.service;

import java.util.List;

import org.factoriaf5.ecommerce.model.Order;
import org.factoriaf5.ecommerce.model.User;
import org.factoriaf5.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public Order save(Order order){
        return orderRepository.save(order);
    }

    public List<Order> findByUser(User user){
        return orderRepository.findByUser(user);
    }

    public Order findByOrderNumber(Long orderNumber){
        return orderRepository.findByOrderNumber(orderNumber).orElse(null);
    }
}
