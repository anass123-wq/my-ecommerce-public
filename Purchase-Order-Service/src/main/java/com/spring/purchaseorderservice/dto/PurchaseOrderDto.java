package com.spring.purchaseorderservice.dto;



import com.spring.purchaseorderservice.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PurchaseOrderDto {
    private Long id;
    private String supplier;
    private double totalAmount;
    private Date date;
    private PaymentStatus paymentStatus;
    @OneToMany(mappedBy = "purchaseOrderDto", cascade = CascadeType.ALL)
    private List<PurchaseLineDto> purchaseLines = new ArrayList<>();


}
