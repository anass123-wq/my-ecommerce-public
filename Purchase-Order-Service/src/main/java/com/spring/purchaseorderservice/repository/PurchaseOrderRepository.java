package com.spring.purchaseorderservice.repository;

import com.spring.purchaseorderservice.model.PaymentStatus;
import com.spring.purchaseorderservice.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    PurchaseOrder findByDate(Date date);
   // PurchaseOrder findByTotalAmout(Double totalAmout);
    PurchaseOrder findByPaymentStatus(PaymentStatus paymentStatus);
    @Query("SELECT pch FROM PurchaseOrder pch WHERE pch.totalAmount >= :amountPurch OR pch.supplier LIKE %:query%")
    List<PurchaseOrder> searchPurchaseOrder(@Param("amountPurch") Double amountPurch, @Param("query") String query);

}
