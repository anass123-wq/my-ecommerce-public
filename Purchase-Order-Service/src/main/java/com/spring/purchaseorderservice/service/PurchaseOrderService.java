package com.spring.purchaseorderservice.service;

import com.spring.purchaseorderservice.config.FeignClientSupplierService;
import com.spring.purchaseorderservice.config.FeignProductService;
import com.spring.purchaseorderservice.dto.PurchaseLineDto;
import com.spring.purchaseorderservice.dto.PurchaseOrderDto;
import com.spring.purchaseorderservice.model.PaymentStatus;
import com.spring.purchaseorderservice.model.PurchaseLine;
import com.spring.purchaseorderservice.model.PurchaseOrder;
import com.spring.purchaseorderservice.repository.PurchaseLineRepository;
import com.spring.purchaseorderservice.repository.PurchaseOrderRepository;
import com.spring.purchaseorderservice.repository.SupplierClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseLineRepository purchaseLineRepository;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private FeignProductService feignProductService;
    @Autowired
    FeignClientSupplierService feignClientSupplierService;
    @Autowired
    SupplierClientRepository supplierClientRepository;
    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) throws IllegalArgumentException {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplier(purchaseOrderDto.getSupplier());
        purchaseOrder.setDate(new Date());
        double totalAmount = 0;
        for (PurchaseLineDto line : purchaseOrderDto.getPurchaseLines()) {
            PurchaseLine purchaseLine = new PurchaseLine();
            purchaseLine.setProductId(line.getProductId());
            purchaseLine.setQuantity(line.getQuantity());
            purchaseLine.setPrice(line.getPrice());
            // Add product stock
            feignProductService.addStock(purchaseLine.getProductId(), purchaseLine.getQuantity());
            feignProductService.updateDate(purchaseLine.getProductId(),purchaseOrder.getDate());
            purchaseLine.setPurchaseOrder(purchaseOrder);
            purchaseOrder.getPurchaseLines().add(purchaseLine);
            totalAmount += line.getPrice() * line.getQuantity();
            purchaseLineRepository.save(purchaseLine);
        }
        feignClientSupplierService.updateTotalOrder(purchaseOrder.getSupplier() ,totalAmount);
        purchaseOrder.setTotalAmount(totalAmount);
        purchaseOrder.setPaymentStatus(purchaseOrderDto.getPaymentStatus());
        return purchaseOrderRepository.save(purchaseOrder);

    }

    public PurchaseOrder getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found with id: " + id));
    }
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }
    public PurchaseOrder getPurchaseOrderByPaymentStatus(PaymentStatus paymentStatus){
        return purchaseOrderRepository.findByPaymentStatus(paymentStatus);
    }
    public List<PurchaseLine> getPurchaseLine(){
        return purchaseLineRepository.findAll();
    }
    public PurchaseOrder getPurchaseOrderByDate(Date date){
        return purchaseOrderRepository.findByDate(date);
    }
    public PurchaseOrder deletePurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        purchaseOrderRepository.delete(purchaseOrder);
        return purchaseOrder;
    }
    public PurchaseOrder updatePurchaseOrder(long id ,PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        purchaseOrder.setSupplier(purchaseOrderDto.getSupplier());
        purchaseOrder.setTotalAmount(purchaseOrderDto.getTotalAmount());
        purchaseOrder.setPaymentStatus(purchaseOrderDto.getPaymentStatus());
        purchaseOrder.setDate(purchaseOrderDto.getDate());
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> searchPurchaseInvoices(String query) {
        Double totalAmount = null;
        try {
            totalAmount = Double.parseDouble(query);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return purchaseOrderRepository.searchPurchaseOrder(totalAmount ,query);
    }

    public PurchaseLine updatePurchaseLine(Long id, PurchaseLine purchaseLine) {
        PurchaseLine line = purchaseLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PurchaseLine not found"));

        PurchaseOrder order = purchaseLine.getPurchaseOrder();
        double oldTotal = line.getPrice() * line.getQuantity();
        double newTotal = purchaseLine.getPrice() * purchaseLine.getQuantity();

        order.setTotalAmount(order.getTotalAmount() - oldTotal + newTotal);

        line.setProductId(purchaseLine.getProductId());
        line.setQuantity(purchaseLine.getQuantity());
        line.setPrice(purchaseLine.getPrice());

        purchaseLineRepository.save(purchaseLine);
        purchaseOrderRepository.save(order);

        return line;
    }

    public PurchaseLine deleteSalesLine(Long id) {
        PurchaseLine purchaseLine = purchaseLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("purchaseLine not found"));

        purchaseLineRepository.delete(purchaseLine);
        return purchaseLine;
    }

    }
