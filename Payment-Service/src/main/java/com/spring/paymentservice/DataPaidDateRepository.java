package com.spring.paymentservice;

import com.spring.paymentservice.model.DataPaidDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPaidDateRepository extends JpaRepository<DataPaidDate, Long> {

}
