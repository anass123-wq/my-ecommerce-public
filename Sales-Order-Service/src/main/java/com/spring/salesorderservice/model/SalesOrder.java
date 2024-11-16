package com.spring.salesorderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double totalAmount;
    private String customer;
    private Date date;
    private PaymentStatus paymentStatus;
    @OneToMany(mappedBy = "salesOrder",cascade = CascadeType.ALL)
    private List<SalesLine> salesLines = new ArrayList<>(); ;

}
