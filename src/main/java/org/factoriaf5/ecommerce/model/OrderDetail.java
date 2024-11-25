package org.factoriaf5.ecommerce.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    Long id;
    String name;
    Integer quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;
}
