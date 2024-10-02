package com.spring.purchaseorderservice.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PurchaseOrderDto {
    private Long id;
    private SupplierClientDto supplier;
    private double totalAmount;
    private List<PurchaseLineDto> purchaseLines = new ArrayList<>();
}
