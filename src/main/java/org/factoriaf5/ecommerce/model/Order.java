package org.factoriaf5.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String orderNumber;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private BigDecimal orderValue;
}
