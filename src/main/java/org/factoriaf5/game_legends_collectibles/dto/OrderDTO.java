package org.factoriaf5.game_legends_collectibles.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(Long orderNumber,LocalDateTime createdAt,BigDecimal totalAmount) {
    
}
