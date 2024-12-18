package com.spring.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
public class GatewayConfig {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.path("/Security/**")
                        .uri("lb://Security"))
                .route(r -> r.path("/Product-Service/**")
                        .uri("lb://Product-Service"))
                .route( r -> r.path("/Sales-Order-Service/**")
                        .uri("lb://Sales-Order-Service"))
                .route( r -> r.path("/Purchase-Order-Service/**")
                        .uri("lb://Purchase-Order-Service"))
                .route( r -> r.path("/SupplierClient-Service/**")
                        .uri("lb://SupplierClient-Service"))
                .route( r -> r.path("/Filter-Service/**")
                        .uri("lb://Filter-Service"))
                .build();

    }

}

