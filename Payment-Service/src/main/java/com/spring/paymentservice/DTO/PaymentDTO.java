package com.spring.paymentservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long OrderId;
    private String ordreType;
    private double amountPaid;
    private double totalAmount;
    @UniqueElements
    private String nameSupplierClient;
}
