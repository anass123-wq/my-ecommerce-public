package com.spring.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}
/*
 * @RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public Payment payOrder(@RequestParam Long salesOrderId, @

* @FeignClient(name = "product-service", url = "http://localhost:8081/products")
public interface FeignProductService {

    @PutMapping("/{id}/reduceStock")
    void reduceStock(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);

    @PutMapping("/{id}/addStock")
    void addStock(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);

    @GetMapping("/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);
}

*/