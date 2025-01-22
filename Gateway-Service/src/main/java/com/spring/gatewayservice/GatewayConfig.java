package com.spring.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
public class GatewayConfig {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("product-service", r -> r.path("/api/products/**")
                        .uri("lb://Product-Service"))
                .route("sales-order-service", r -> r.path("/sales/**")
                        .uri("lb://Sales-Order-Service"))
                .route("purchase-order-service", r -> r.path("/purchases/**")
                        .uri("lb://Purchase-Order-Service"))
                .route("supplier-client-service", r -> r.path("/supplier-clients/**")
                        .uri("lb://SupplierClient-Service"))
                .route("filter-service", r -> r.path("/api/Filters/**")
                        .uri("lb://Filter-Service"))
                .route("Security-Service", r -> r.path("/users/**")
                        .uri("lb://Security-Service"))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",  // Add your frontend origin if needed
                "http://localhost:3003",
                "http://localhost:3004",
                "http://localhost:3005",
                "http://localhost:3006",
                "http://localhost:3007",
                "http://localhost:3008"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization"));  // Allow frontend to access Authorization header
        configuration.setAllowCredentials(true);  // Allow cookies and credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

