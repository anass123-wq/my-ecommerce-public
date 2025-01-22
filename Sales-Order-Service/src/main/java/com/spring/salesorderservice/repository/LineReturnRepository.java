package com.spring.salesorderservice.repository;

import com.spring.salesorderservice.model.LineReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineReturnRepository extends JpaRepository<LineReturn, Integer> {
}
