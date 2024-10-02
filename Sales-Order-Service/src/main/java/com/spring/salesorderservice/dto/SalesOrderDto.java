package com.spring.salesorderservice.dto;

import com.spring.salesorderservice.model.SalesLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SalesOrderDto {
    private Long id;
    private SupplierClientDto customer;
    private double totalAmount;
    private boolean isPaid;
    private List<SalesLineDto> salesLines = new ArrayList<>();
}
