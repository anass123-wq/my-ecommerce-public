package com.spring.purchaseorderservice.config;

import com.spring.purchaseorderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@Component
@FeignClient(name = "product-service", url = "http://localhost:8031/api/products")
public interface FeignProductService {

    @PutMapping("/{id}/addStock")
    void addStock(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);
    @PutMapping("/{id}/updateDate")
    void updateDate(@PathVariable("id") Long id, @RequestParam("date") /*@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)*/ Date date);
}
