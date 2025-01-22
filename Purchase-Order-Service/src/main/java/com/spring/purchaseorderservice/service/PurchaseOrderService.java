package com.spring.purchaseorderservice.service;

import com.spring.purchaseorderservice.config.FeignClientSupplierService;
import com.spring.purchaseorderservice.config.FeignPaiement;
import com.spring.purchaseorderservice.config.FeignProductService;
import com.spring.purchaseorderservice.dto.PurchaseLineDto;
import com.spring.purchaseorderservice.dto.PurchaseOrderDto;
import com.spring.purchaseorderservice.model.PaymentStatus;
import com.spring.purchaseorderservice.model.PurchaseLine;
import com.spring.purchaseorderservice.model.PurchaseOrder;
import com.spring.purchaseorderservice.repository.PurchaseLineRepository;
import com.spring.purchaseorderservice.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
    private FeignPaiement feignPaiement;
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
        purchaseOrder.setPaymentStatus(purchaseOrderDto.getPaymentStatus());
        feignClientSupplierService.updateTotalOrder(purchaseOrder.getSupplier() ,totalAmount,"PurchService" ,purchaseOrder.getPaymentStatus());
        purchaseOrder.setTotalAmount(totalAmount);
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
        if(purchaseOrderDto.getTotalAmount()!=purchaseOrder.getTotalAmount()){
            purchaseOrder.setTotalAmount(purchaseOrderDto.getTotalAmount());
            feignPaiement.updatePayment(purchaseOrder.getTotalAmount());
        }
        purchaseOrder.setPaymentStatus(purchaseOrderDto.getPaymentStatus());
        purchaseOrder.setDate(purchaseOrderDto.getDate());
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> searchPurchaseInvoices(String query) {
        Double totalAmount = null;
        PaymentStatus paymentStatus = null;
        Date date = null;
        try {
            totalAmount = Double.parseDouble(query);
            paymentStatus = PaymentStatus.valueOf(query);
            date = Date.from(Instant.parse(query));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return purchaseOrderRepository.searchPurchaseOrder(totalAmount ,paymentStatus,date,query);
    }


    public List<PurchaseLine> getLinesByPurchaseOrderId(Long id){
        return purchaseLineRepository.findPurchaseLineByPurchaseOrderId(Math.toIntExact(id));
    }
    public PurchaseOrder getPurchaseOrderByPurchaseLineId(Long id) {
        PurchaseLine purchaseLine = purchaseLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("purchaseLine not found"));

        return purchaseOrderRepository.findByPurchaseLinesContaining(purchaseLine);
    }

    }
