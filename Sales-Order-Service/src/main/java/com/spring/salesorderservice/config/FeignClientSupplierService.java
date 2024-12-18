package com.spring.salesorderservice.config;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SupplierClient-Service")
public interface FeignClientSupplierService {

    @Bean
     default RequestInterceptor feignRequestInterceptor() {
        return requestTemplate -> {
            String serviceName = "SalesService";
            requestTemplate.header("Source-Service", serviceName);
        };
    }

    @PutMapping("/SupplierClients/{totale}/{name}")
    void updateTotalOrder(@PathVariable("name") String name ,@PathVariable("totale")double totale);

}
