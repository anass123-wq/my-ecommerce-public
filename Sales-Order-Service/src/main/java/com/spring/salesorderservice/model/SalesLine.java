package com.spring.salesorderservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SalesLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "sales_order_id")
    private SalesOrder salesOrder;

    private Long productId;

}
