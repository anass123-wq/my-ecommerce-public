package com.spring.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}
/*
* @Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long salesOrderId;
    private double amountPaid;

    // Getters and Setters
}
* @Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Transactional
    public Payment paySalesOrder(Long salesOrderId, double amount) {
        SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sales Order not found with id: " + salesOrderId));

        Payment payment = new Payment();
        payment.setSalesOrderId(salesOrderId);
        payment.setAmountPaid(amount);

        double remainingAmount = salesOrder.getTotalAmount() - amount;
        if (remainingAmount <= 0) {
            salesOrder.setPaid(true);
        }
        salesOrderRepository.save(salesOrder);

        return paymentRepository.save(payment);
    }

    public PaymentStatus getPaymentStatus(Long salesOrderId) {
        SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sales Order not found with id: " + salesOrderId));
        return salesOrder.isPaid() ? PaymentStatus.PAID : PaymentStatus.UNPAID;
    }
}

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

* public class ProductDto {

    private Long id;
    private String name;
    private int quantity;
    private double price;

    // Getters and Setters
}
*/