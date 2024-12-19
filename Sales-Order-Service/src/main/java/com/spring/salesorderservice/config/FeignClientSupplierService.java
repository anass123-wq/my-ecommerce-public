package com.spring.salesorderservice.config;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SupplierClient-Service" ,url = "http://localhost:8034/SupplierClients")
public interface FeignClientSupplierService {

    @Bean
     default RequestInterceptor feignRequestInterceptor() {
        return requestTemplate -> {
            String serviceName = "SalesService";
            requestTemplate.header("Source-Service", serviceName);
        };
    }

    @PutMapping("/SupplierClients/{name}")
    void updateTotalOrder(@PathVariable("name") String name ,@RequestParam("totale")double totale);

}
