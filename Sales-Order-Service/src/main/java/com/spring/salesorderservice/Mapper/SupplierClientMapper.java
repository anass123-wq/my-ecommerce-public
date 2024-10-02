package com.spring.salesorderservice.Mapper;


import com.spring.salesorderservice.dto.SupplierClientDto;
import com.spring.salesorderservice.model.SupplierClient;

public class SupplierClientMapper {

    public static SupplierClientDto toDto(SupplierClient supplierClient) {
        return new SupplierClientDto(
                supplierClient.getId(),
                supplierClient.getName(),
                supplierClient.getEmail(),
                supplierClient.getPhoneNumber()
        );
    }

    public static SupplierClient toEntity(SupplierClientDto supplierClientDto) {
        return new SupplierClient(
                supplierClientDto.getId(),
                supplierClientDto.getName(),
                supplierClientDto.getEmail(),
                supplierClientDto.getPhoneNumber()
        );
    }
}
