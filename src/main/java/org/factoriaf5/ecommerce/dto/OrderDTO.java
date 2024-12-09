package org.factoriaf5.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(Long orderNumber,LocalDateTime createdAt,BigDecimal totalAmount) {
    
}
