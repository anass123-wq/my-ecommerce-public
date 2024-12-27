package com.spring.purchaseorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductDto{
private Long id;
private String name;
private String description;
private int quantity;
private double price;
private double priceInit;
private String image;
private String category;
private Date dateCreating;
}
