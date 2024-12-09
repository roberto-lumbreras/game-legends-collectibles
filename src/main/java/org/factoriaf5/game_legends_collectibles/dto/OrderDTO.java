package org.factoriaf5.game_legends_collectibles.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderDTO(Long orderNumber,LocalDate createdAt,BigDecimal totalAmount) {
    
}
