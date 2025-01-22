package com.spring.paymentservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataPaidDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amountPaid;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "payment_id", nullable = false)
    @JsonBackReference
    private Long PaymentId;
}