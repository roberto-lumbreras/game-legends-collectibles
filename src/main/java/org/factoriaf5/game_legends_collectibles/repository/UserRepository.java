package org.factoriaf5.game_legends_collectibles.repository;

import java.util.Optional;

import org.factoriaf5.game_legends_collectibles.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);
}