package com.spring.salesorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SupplierClientDto {
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
}
