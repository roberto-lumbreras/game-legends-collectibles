package org.factoriaf5.game_legends_collectibles.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String productName;
}
