package com.spring.supplierclientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SupplierClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplierClientServiceApplication.class, args);
	}

}
