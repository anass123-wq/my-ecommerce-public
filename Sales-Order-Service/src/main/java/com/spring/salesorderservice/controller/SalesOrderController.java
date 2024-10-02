package com.spring.salesorderservice.controller;

import com.spring.salesorderservice.dto.SalesOrderDto;
import com.spring.salesorderservice.model.SalesOrder;
import com.spring.salesorderservice.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @PostMapping("/create")
    public SalesOrder createSalesOrder(@RequestBody SalesOrderDto salesOrderDto , String name) {
        return salesOrderService.createSalesOrder(salesOrderDto , name);
    }

    @GetMapping("/{id}")
    public SalesOrder getSalesOrder(@PathVariable Long id) {
        return salesOrderService.getSalesOrderById(id);
    }
}
