package com.spring.purchaseorderservice.controller;

import com.spring.purchaseorderservice.dto.ProductDto;
import com.spring.purchaseorderservice.dto.PurchaseLineDto;
import com.spring.purchaseorderservice.dto.PurchaseOrderDto;
import com.spring.purchaseorderservice.model.PaymentStatus;
import com.spring.purchaseorderservice.model.PurchaseLine;
import com.spring.purchaseorderservice.model.PurchaseOrder;
import com.spring.purchaseorderservice.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/purchases")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/create")
    public PurchaseOrder createPurchaseOrder(@RequestBody PurchaseOrderDto purchaseOrderDto /*,@RequestParam("name") String name*/) {
        System.out.println(purchaseOrderDto);
        return purchaseOrderService.createPurchaseOrder(purchaseOrderDto);
    }

    @GetMapping("/{id}")
    public PurchaseOrder getPurchaseOrder(@PathVariable Long id) {
        return purchaseOrderService.getPurchaseOrderById(id);
    }

    @GetMapping
    public List<PurchaseOrderDto> getPurchaseOrders() {
        List<PurchaseOrder> orders = purchaseOrderService.getAllPurchaseOrders();
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private PurchaseOrderDto convertToDto(PurchaseOrder order) {
        List<PurchaseLineDto> lineDtos = order.getPurchaseLines().stream()
                .map(line -> new PurchaseLineDto(line.getQuantity(), line.getPrice(), line.getProductId())) // Adjust as needed
                .collect(Collectors.toList());

        return new PurchaseOrderDto(order.getId(), order.getSupplier(), order.getTotalAmount(), order.getDate(), order.getPaymentStatus(), lineDtos);
    }
    @GetMapping("/{date}")
    public PurchaseOrder getSalesOrderByDate(@PathVariable Date date){
        return purchaseOrderService.getPurchaseOrderByDate(date);
    }
    @GetMapping("/{paymentStatus}")
    public PurchaseOrder getSalesOrderByPaymentStatus(@PathVariable PaymentStatus paymentStatus){
        return purchaseOrderService.getPurchaseOrderByPaymentStatus(paymentStatus);
    }
    @DeleteMapping("/delete/{id}")
    public PurchaseOrder deleteSalesOrder(@PathVariable Long id){
        return purchaseOrderService.deletePurchaseOrder(id);
    }

    @PutMapping("/update/{id}")
    public PurchaseOrder updatePurchaseInvoice(@PathVariable Long id, @RequestBody PurchaseOrderDto purchaseOrderDto) throws Exception {
        return purchaseOrderService.updatePurchaseOrder(id, purchaseOrderDto);
    }
    @GetMapping("/searchPurchaseOrder")
    public ResponseEntity<List<PurchaseOrder>> searchPurchaseInvoices(@RequestParam String query) {
        List<PurchaseOrder> results = purchaseOrderService.searchPurchaseInvoices(query);
        return ResponseEntity.ok(results);
    }
    @GetMapping("/lines")
    public List<PurchaseLine> getPurchaseLines(){
        return purchaseOrderService.getPurchaseLine();
    }

    @GetMapping("/lines/order/{id}")
    public List<PurchaseLine> getSalesLinesBySalesOrderId(@PathVariable("id") Long id){
        return purchaseOrderService.getLinesByPurchaseOrderId(id);
    }
    @GetMapping("/line/{id}/order")
    public PurchaseOrder getPurchaseOrderByPurchaseLineId(@PathVariable("id") Long id){
        return purchaseOrderService.getPurchaseOrderByPurchaseLineId(id);
    }
}


