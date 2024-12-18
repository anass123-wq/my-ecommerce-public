package com.spring.purchaseorderservice.repository;

import com.spring.purchaseorderservice.model.PurchaseLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseLineRepository extends JpaRepository<PurchaseLine, Long> {
    /*PurchaseLine findByPurchaseOrderId(Long id);
    PurchaseLine findByPurchaseOrderPrice(Double Price);
    PurchaseLine findByPurchaseOrderQuantity(Integer Quantity);
    PurchaseLine findByPurchaseLineId(Long id);*/
}
