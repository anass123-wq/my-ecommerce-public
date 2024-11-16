package com.spring.salesorderservice.controller;

import com.spring.salesorderservice.dto.SalesLineDto;
import com.spring.salesorderservice.dto.SalesOrderDto;
import com.spring.salesorderservice.model.PaymentStatus;
import com.spring.salesorderservice.model.SalesLine;
import com.spring.salesorderservice.model.SalesOrder;
import com.spring.salesorderservice.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
@RestController
@RequestMapping("/sales")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @PostMapping("/create")
    public SalesOrder create(@RequestBody SalesOrderDto salesOrderDto) {
        PaymentStatus paymentStatus = salesOrderDto.getPaymentStatus();
         if (paymentStatus == null) {
             salesOrderDto.setPaymentStatus(PaymentStatus.UNPAID);
        }
        System.out.println(salesOrderDto);
        return salesOrderService.createSalesOrder(salesOrderDto);
    }

    @GetMapping
    public List<SalesOrderDto> getAllSalesOrders() {
        List<SalesOrder> orders = salesOrderService.getAllSalesOrders();
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private SalesOrderDto convertToDto(SalesOrder order) {
        List<SalesLineDto> lineDtos = order.getSalesLines().stream()
                .map(line -> new SalesLineDto(line.getQuantity(), line.getPrice(), line.getProductId())) // Adjust as needed
                .collect(Collectors.toList());

        return new SalesOrderDto(order.getId(), order.getCustomer(), order.getTotalAmount(), order.getDate(), order.getPaymentStatus(), lineDtos);
    }

    @GetMapping("/{id}")
    public SalesOrder getSalesOrder(@PathVariable Long id) {
        return salesOrderService.getSalesOrderById(id);
    }
    @GetMapping("/{totalAmount}")
    public Optional<SalesOrder> getSalesOrderByTotalAmount(@PathVariable Double totalAmount){
        return salesOrderService.getSalesOrderByTotalAmount(totalAmount);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSalesOrder(@PathVariable Long id){
         salesOrderService.deleteSalesOrder(id);
    }
    @PutMapping("/update/{id}")
    public SalesOrder updateSalesInvoice(@PathVariable Long id, @RequestBody SalesOrderDto salesOrderDto){
        return salesOrderService.updateSalesInvoice(id, salesOrderDto);
    }
    /*
    @GetMapping("/by-payment-status")
    public SalesOrder getSalesOrderByPaymentStatus(@RequestParam PaymentStatus status) {
        return salesOrderService.getSalesOrderByPaymentStatus(status);
    }

    @GetMapping("/by-date")
    public SalesOrder getSalesOrderByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return salesOrderService.getSalesOrderByDate(date);
    }*/
    @GetMapping("/searchSalesOrder")
    public ResponseEntity<List<SalesOrder>> searchSalesInvoices(@RequestParam String query) {
        List<SalesOrder> results = salesOrderService.searchSalesInvoices(query);
        return ResponseEntity.ok(results);
    }
    @PutMapping("/line/{id}")
    public ResponseEntity<SalesLine> updateSalesLine(@PathVariable Long id, @RequestBody SalesLineDto salesLineDto) {
        SalesLine updatedSalesLine = salesOrderService.updateSalesLine(id, salesLineDto);
        return ResponseEntity.ok(updatedSalesLine);
    }

    @DeleteMapping("/line/{id}")
    public ResponseEntity<SalesLine> deleteSalesLine(@PathVariable Long id) {
        SalesLine deletedSalesLine = salesOrderService.deleteSalesLine(id);
        return ResponseEntity.ok(deletedSalesLine);
    }

    @GetMapping("/line/{id}/order")
    public ResponseEntity<SalesOrder> getSalesOrderBySalesLineId(@PathVariable Long id) {
        SalesOrder salesOrder = salesOrderService.getSalesOrderBySalesLineId(id);
        return ResponseEntity.ok(salesOrder);
    }
    @GetMapping("lines")
    public List<SalesLine> getSalesLines() {
        return salesOrderService.getSalesLines();
    }
}