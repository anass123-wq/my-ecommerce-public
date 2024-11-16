package com.spring.purchaseorderservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/purchase-websocket").setAllowedOrigins("http://localhost:3004"
                ,"http://localhost:3006"
                ,"http://localhost:3007"
                ,"http://localhost:3008"
                ,"http://localhost:3003"
                ,"http://localhost:3000"
                ,"http://localhost:3005").withSockJS();
    }
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setSendBufferSizeLimit(512 * 1024); // Optional: Set buffer size if needed
        registry.setMessageSizeLimit(128 * 1024); // Optional: Set message size limit if needed
    }

}