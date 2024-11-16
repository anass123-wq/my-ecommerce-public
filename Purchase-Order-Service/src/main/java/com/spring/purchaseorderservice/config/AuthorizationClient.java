package com.spring.purchaseorderservice.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Security-Service")
public interface AuthorizationClient {

    @GetMapping("/permissions/{permissionName}")
     boolean doesPermissionExist(@PathVariable("permissionName") List<String> permissionName);
}
