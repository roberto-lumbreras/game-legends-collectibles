package org.factoriaf5.ecommerce.dto;

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
    private Integer amount;
    private BigDecimal unitPrice;
    private String productName;

    public CartItem(Long productId, Integer amount, BigDecimal unitPrice) {
        this.productId = productId;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }
    
}
