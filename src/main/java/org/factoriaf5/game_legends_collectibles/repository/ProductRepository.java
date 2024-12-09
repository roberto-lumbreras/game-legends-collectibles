package org.factoriaf5.game_legends_collectibles.repository;

import org.factoriaf5.game_legends_collectibles.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
