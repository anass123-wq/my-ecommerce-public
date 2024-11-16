package com.spring.salesorderservice.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "Product-Service" ,url = "http://localhost:8031/api/products")
public interface FeignProductService {

    @PutMapping("/{productId}/reduceStock")
    void reduceStock(@PathVariable("productId") Long productId, @RequestParam("quantity") int quantity);

}