package com.spring.purchaseorderservice.repository;

import com.spring.purchaseorderservice.model.PaymentStatus;
import com.spring.purchaseorderservice.model.PurchaseLine;
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
    PurchaseOrder findByPurchaseLinesContaining(PurchaseLine purchaseLine);
    PurchaseOrder findByPaymentStatus(PaymentStatus paymentStatus);
    @Query(value = "SELECT pch FROM PurchaseOrder pch WHERE pch.totalAmount >= :amountPurch OR pch.paymentStatus = :paymentStatus OR pch.date = :date OR pch.supplier LIKE %:query%")
    List<PurchaseOrder> searchPurchaseOrder(@Param("amountPurch") Double amountPurch,@Param("paymentStatus")PaymentStatus paymentStatus,@Param("date") Date date, @Param("query") String query);

}
