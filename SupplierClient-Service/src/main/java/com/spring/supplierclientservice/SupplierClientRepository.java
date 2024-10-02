package com.spring.supplierclientservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierClientRepository extends JpaRepository<SupplierClient, Long> {
    SupplierClient findByName(String name);
    SupplierClient findById(long id);
}
