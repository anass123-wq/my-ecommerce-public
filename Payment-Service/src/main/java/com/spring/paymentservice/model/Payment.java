package com.spring.paymentservice.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long OrderId;
    private String ordreType;
    private double amountPaid;
    private double totalAmount;
    @UniqueElements
    private String nameSupplierClient;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "payment_id") // This creates the foreign key in the DataPaidDate table
    @JsonManagedReference
    private List<DataPaidDate> paidDates = new ArrayList<>();
}
