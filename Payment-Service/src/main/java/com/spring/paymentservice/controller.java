package com.spring.paymentservice;

import com.spring.paymentservice.DTO.PaymentDTO;
import com.spring.paymentservice.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
@RestController
@RequestMapping("/payment")
public class controller {
    @Autowired
    private PaymentService paymentService;
    // POST: Add a new payment
    @PostMapping
    public ResponseEntity<Payment> addPayment(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = paymentService.addPayment(paymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    // PUT: Update payment details for a specific order
    @PutMapping("/update/{orderId}")
    public ResponseEntity<Payment> updatePaymentOrder(
            @PathVariable("orderId") Long orderId,
            @RequestParam double amount,
            @RequestParam String status) {
        Payment updatedPayment = paymentService.UpdatePayOrder(orderId, amount, status);
        return ResponseEntity.ok(updatedPayment);
    }

    // GET: Retrieve a payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentId(Math.toIntExact(id));
        return ResponseEntity.ok(payment);
    }

    // GET: Retrieve all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
 /*  @PostMapping
    public Payment addPayment(@RequestParam PaymentDTO paymentDTO) {
        return paymentService.addPayment(paymentDTO);
    }
    @PutMapping("/UpdatePay/{OrderId}")
    public Payment UpdatePayOrder(@PathVariable Long OrderId,@RequestParam double amount,@RequestParam String status){
        return paymentService.UpdatePayOrder(OrderId,amount,status);
    }
    @GetMapping("/{id}")
    public Payment getPaymentId(@PathVariable int id) {
        return paymentService.getPaymentId(id);
    }
    @GetMapping
    public List<Payment> getPayments() {
        return paymentService.getAllPayments();
    }*/
}
