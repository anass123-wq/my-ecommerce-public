package com.spring.purchaseorderservice.repository;

import com.spring.purchaseorderservice.model.PurchaseLine;
import com.spring.purchaseorderservice.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseLineRepository extends JpaRepository<PurchaseLine, Long> {
    /*PurchaseLine findByPurchaseOrderId(Long id);
    PurchaseLine findByPurchaseOrderPrice(Double Price);
    PurchaseLine findByPurchaseOrderQuantity(Integer Quantity);
    PurchaseLine findByPurchaseLineId(Long id);*/
    /*List<PurchaseLine> findByPriceContaining(double price);*/
    List<PurchaseLine> findByPurchaseOrderId(Integer id);
}
