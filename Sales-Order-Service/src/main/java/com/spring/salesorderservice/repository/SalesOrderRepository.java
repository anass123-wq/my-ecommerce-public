package com.spring.salesorderservice.repository;

import com.spring.salesorderservice.model.PaymentStatus;
import com.spring.salesorderservice.model.SalesLine;
import com.spring.salesorderservice.model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    @Query("SELECT s FROM SalesOrder s WHERE s.totalAmount >= :amount OR s.customer LIKE %:query%")
    List<SalesOrder> searchSalesOrder(@Param("amount") Double amount, @Param("query") String query);
    SalesOrder findByDate(Date date);
    Optional<SalesOrder> findByTotalAmount(Double totalAmount);
    SalesOrder findByPaymentStatus(PaymentStatus paymentStatus);
    @Modifying
    @Query("DELETE FROM SalesLine sl WHERE sl.salesOrder.id = :orderId")
    void deleteBySalesOrderId(@Param("orderId") Long orderId);
    SalesOrder findBySalesLinesContaining(SalesLine salesLine);
    List<SalesOrder> findByCustomer(String customer);
}
