package com.spring.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

public class GatewayConfig {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.path("/Product-Service/**")
                        .uri("lb://Product-Service"))
                .route( r -> r.path("/Sales-Order-Service/**")
                        .uri("lb://Sales-Order-Service"))
                .route( r -> r.path("/Purchase-Order-Service/**")
                        .uri("lb://Purchase-Order-Service"))
                .route( r -> r.path("/SupplierClient-Service/**")
                        .uri("lb://SupplierClient-Service\n"))
                .build();
    }
}

