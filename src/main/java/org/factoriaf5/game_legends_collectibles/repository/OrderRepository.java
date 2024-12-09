package org.factoriaf5.game_legends_collectibles.repository;

import org.factoriaf5.game_legends_collectibles.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.factoriaf5.game_legends_collectibles.model.User;


public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
    Optional<Order> findByOrderNumber(Long orderNumber);
}
