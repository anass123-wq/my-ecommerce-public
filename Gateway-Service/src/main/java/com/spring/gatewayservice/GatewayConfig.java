package com.spring.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:3003", "http://localhost:3004",
        "http://localhost:3005", "http://localhost:3006", "http://localhost:3007", "http://localhost:3008"
})
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/api/products/**")
                        .uri("lb://Product-Service"))
                .route("sales-order-service", r -> r.path("/sales/**")
                        .or().path("/line/sales/**")
                        .or().path("/lineReturn/sales/**")
                        .uri("lb://Sales-Order-Service"))
                .route("purchase-order-service", r -> r.path("/purchases/**")
                        .or().path("/lineReturn/purchases/**")
                        .or().path("/line/purchases/**")
                        .uri("lb://Purchase-Order-Service"))
                .route("supplier-client-service", r -> r.path("/SupplierClients/**")
                        .uri("lb://SupplierClient-Service"))
                .route("filter-service", r -> r.path("/api/Filters/**")
                        .uri("lb://Filter-Service"))
                .route("security-service", r -> r.path("/users/**")
                        .uri("lb://Security-Service"))
                .route("purchase-order-service",r -> r.path("/line/purchases/**")
                        .uri("lb://Purchase-Order-Service"))
                .route("purchase-order-service",r -> r.path("/lineReturn/sales/**")
                        .uri("lb://Purchase-Order-Service"))
                .route("sales-order-service", r -> r.path("/line/sales/**")
                        .uri("lb://Sales-Order-Service"))
                .route("sales-order-service", r -> r.path("/lineReturn/sales/**")
                        .uri("lb://Sales-Order-Service"))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:3003",
                "http://localhost:3004",
                "http://localhost:3005",
                "http://localhost:3006",
                "http://localhost:3007",
                "http://localhost:3008"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
