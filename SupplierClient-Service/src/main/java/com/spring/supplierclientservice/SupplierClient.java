package com.spring.supplierclientservice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SupplierClient  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( unique = true)
    private String name;
    private String email;
    private String phoneNumber;
    private double totalSeles;
    private double totalePurch;
    private double borrowingPricSeles;
    private double borrowingPricPurch;
}