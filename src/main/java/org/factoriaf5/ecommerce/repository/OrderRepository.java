package org.factoriaf5.ecommerce.repository;

import org.factoriaf5.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.factoriaf5.ecommerce.model.User;


public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
    Optional<Order> findByOrderNumber(Long orderNumber);
}
