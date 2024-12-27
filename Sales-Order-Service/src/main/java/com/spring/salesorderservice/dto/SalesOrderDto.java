package com.spring.salesorderservice.dto;

import com.spring.salesorderservice.model.PaymentStatus;
import com.spring.salesorderservice.model.SalesLine;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SalesOrderDto {
    private Long id;
    private String customer;
    private double totalAmount;
    private Date date;
    private PaymentStatus paymentStatus;
    @OneToMany(mappedBy = "purchaseOrderDto", cascade = CascadeType.ALL)
    private List<SalesLineDto> salesLines= new ArrayList<>() ;

}
