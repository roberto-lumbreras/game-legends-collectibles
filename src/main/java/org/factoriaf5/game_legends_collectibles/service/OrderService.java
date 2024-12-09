package org.factoriaf5.game_legends_collectibles.service;

import java.util.List;

import org.factoriaf5.game_legends_collectibles.model.Order;
import org.factoriaf5.game_legends_collectibles.model.User;
import org.factoriaf5.game_legends_collectibles.repository.OrderRepository;
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
