package com.spring.purchaseorderservice.service;

import com.spring.purchaseorderservice.config.FeignPaiement;
import com.spring.purchaseorderservice.config.FeignProductService;
import com.spring.purchaseorderservice.dto.PurchaseLineDto;
import com.spring.purchaseorderservice.model.PurchaseLine;
import com.spring.purchaseorderservice.model.PurchaseOrder;
import com.spring.purchaseorderservice.repository.PurchaseLineRepository;
import com.spring.purchaseorderservice.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LineService {
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private FeignPaiement feignPaiement;
    @Autowired
    private FeignProductService feignProductService;
    @Autowired
    private PurchaseLineRepository purchaseLineRepository;
    public PurchaseLine deletePurchaseLine(Long id) throws Exception {
        PurchaseLine purchaseLine = purchaseLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SalesLine not found"));
        PurchaseOrder order = purchaseOrderService.getPurchaseOrderByPurchaseLineId(id);
        order.setTotalAmount(order.getTotalAmount()-purchaseLine.getPrice()*purchaseLine.getQuantity());
        purchaseOrderRepository.save(order);
        feignProductService.reduceStock(purchaseLine.getProductId(),purchaseLine.getQuantity());
        feignProductService.updateDate(purchaseLine.getProductId(),new Date());
        feignPaiement.updatePayment(order.getId(),order.getTotalAmount(), String.valueOf(order.getPaymentStatus()));
        purchaseLineRepository.delete(purchaseLine);
        return purchaseLine;
    }
    public PurchaseLine updatePurchaseLine(Long id, PurchaseLineDto purchaseLineDto) throws Exception {
        PurchaseLine purchaseLine = purchaseLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SalesLine not found"));
        feignProductService.reduceStock(purchaseLine.getProductId(),purchaseLine.getQuantity());
        PurchaseOrder order = purchaseLine.getPurchaseOrder();
        double oldTotal = purchaseLine.getPrice() * purchaseLine.getQuantity();
        double newTotal = purchaseLineDto.getPrice() * purchaseLineDto.getQuantity();

        order.setTotalAmount(order.getTotalAmount() - oldTotal + newTotal);
        feignPaiement.updatePayment(order.getId(),order.getTotalAmount(), String.valueOf(order.getPaymentStatus()));
        purchaseLine.setProductId(purchaseLineDto.getProductId());
        purchaseLine.setQuantity(purchaseLineDto.getQuantity());
        purchaseLine.setPrice(purchaseLineDto.getPrice());
        feignProductService.addStock(purchaseLine.getProductId(),purchaseLine.getQuantity());
        feignProductService.updateDate(purchaseLine.getProductId(),new Date());
        purchaseLineRepository.save(purchaseLine);
        purchaseOrderRepository.save(order);

        return purchaseLine;
    }
    public List<PurchaseLine> getPurchaseLines(){
        return purchaseLineRepository.findAll();
    }


}
