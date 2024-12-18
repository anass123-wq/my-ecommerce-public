package com.spring.salesorderservice.repository;

import com.spring.salesorderservice.model.SalesLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesLineRepository extends JpaRepository<SalesLine, Long> {
    /*List<SalesLine> findSalesLineBySalesOrderId(Long id);
    List<SalesLine> findSalesLinesByProductId(Long id);*/
}
