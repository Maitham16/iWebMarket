package com.example.order.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto {
        
        private Long itemId;
        private String skucode;
        private BigDecimal price;
        private Integer quantity;
            
}
