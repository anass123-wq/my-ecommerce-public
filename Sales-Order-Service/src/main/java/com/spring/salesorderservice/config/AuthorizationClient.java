package com.spring.salesorderservice.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Security-Service")
public interface AuthorizationClient {

    @GetMapping("/permissions/{permissionName}")
     boolean doesPermissionExist(@PathVariable("permissionName") String permissionName);
}
