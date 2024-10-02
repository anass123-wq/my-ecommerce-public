package com.spring.salesorderservice.dto;

import com.spring.salesorderservice.model.SalesOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SalesLineDto {
    private Long id;
    private int quantity;
    private double price;
    private SalesOrder salesOrder;

    private Long productId;
}
