package com.spring.purchaseorderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@ManyToOne // private String  supplier;
    //@ManyToOne(fetch = FetchType.LAZY)
    private String  supplier;
    private double totalAmount;
    private Date date;
    private PaymentStatus paymentStatus;
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private List<PurchaseLine> purchaseLines = new ArrayList<>();

}

