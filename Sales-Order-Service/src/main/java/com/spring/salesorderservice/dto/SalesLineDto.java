package com.spring.salesorderservice.dto;

import com.spring.salesorderservice.model.SalesOrder;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SalesLineDto {
    private int quantity;
    private double price;
    private Long productId;
}
