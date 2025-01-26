package com.spring.salesorderservice.service;

import com.spring.salesorderservice.config.FeignClientSupplierService;
import com.spring.salesorderservice.config.FeignPaiement;
import com.spring.salesorderservice.config.FeignProductService;
import com.spring.salesorderservice.dto.SalesLineDto;
import com.spring.salesorderservice.dto.SalesOrderDto;
import com.spring.salesorderservice.model.PaymentStatus;
import com.spring.salesorderservice.model.SalesLine;
import com.spring.salesorderservice.model.SalesOrder;
import com.spring.salesorderservice.repository.SalesLineRepository;
import com.spring.salesorderservice.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class SalesOrderService {
    @Autowired
    FeignClientSupplierService feignClientSupplierService;
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    private FeignProductService feignProductService;
    @Autowired
    private SalesLineRepository salesLineRepository;
    @Autowired
    private FeignPaiement feignPaiement;

    @Transactional
    public SalesOrder createSalesOrder(SalesOrderDto salesOrderDto ) throws IllegalArgumentException {

        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCustomer(salesOrderDto.getCustomer());
        salesOrder.setDate(new Date());
        double totalAmount = 0;

        for (SalesLineDto line : salesOrderDto.getSalesLines()) {
            SalesLine salesLine = new SalesLine();
            salesLine.setProductId(line.getProductId());
            salesLine.setQuantity(line.getQuantity());
            salesLine.setPrice(line.getPrice());

            // Add product stock
            feignProductService.reduceStock(salesLine.getProductId(), salesLine.getQuantity());
            feignProductService.updateDate(salesLine.getProductId(),salesOrder.getDate());

            salesLine.setSalesOrder(salesOrder);
            salesOrder.getSalesLines().add(salesLine);
            totalAmount += line.getPrice() * line.getQuantity();
            salesLineRepository.save(salesLine);
        }salesOrder.setPaymentStatus(salesOrderDto.getPaymentStatus());
        feignClientSupplierService.updateTotalOrder(salesOrder.getCustomer(),totalAmount,"SalesService",salesOrder.getPaymentStatus());
        salesOrder.setTotalAmount(totalAmount);
        return salesOrderRepository.save(salesOrder);

    }
    public SalesOrder getSalesOrderById(Long id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales Order not found with id: " + id));
    }
    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }


    public void deleteSalesOrder(Long id) {
        salesOrderRepository.deleteById(id);
    }
    public Optional<SalesOrder> getSalesOrderByTotalAmount(Double totalAmount) {
        return salesOrderRepository.findByTotalAmount(totalAmount);
    }

    @Transactional
    public SalesOrder updateSalesInvoice( Long id,  SalesOrderDto salesOrderDto){
        Optional<SalesOrder> salesOrder = salesOrderRepository.findById(id);
        if (salesOrder.isPresent()) {
            salesOrder.get().setCustomer(salesOrderDto.getCustomer());
            if(salesOrderDto.getTotalAmount()!=salesOrder.get().getTotalAmount()){
                salesOrder.get().setTotalAmount(salesOrderDto.getTotalAmount());
                feignPaiement.updatePayment(salesOrder.get().getId(),salesOrder.get().getTotalAmount());
            }
            salesOrder.get().setPaymentStatus(salesOrderDto.getPaymentStatus());
            salesOrder.get().setDate(salesOrderDto.getDate());
            salesOrderRepository.save(salesOrder.get());
        }
        return salesOrder.get();
    }
    public List<SalesOrder> searchSalesInvoices(@Param("query") String query) {
        Double totalAmount = null;
        Date date = null;
        PaymentStatus paymentStatus = null;
        try {
            totalAmount = Double.parseDouble(query);
            paymentStatus = PaymentStatus.valueOf(query);
            date = Date.from(Instant.parse(query));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return salesOrderRepository.searchSalesOrder(totalAmount,paymentStatus,date, query);
    }

    public SalesOrder getSalesOrderBySalesLineId(Long id) {
        SalesLine salesLine = salesLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SalesLine not found"));

        return salesOrderRepository.findBySalesLinesContaining(salesLine);
    }
    public List<SalesLine> getSalesLinesBySalesOrderId(Long id) {
        return salesLineRepository.findSalesLinesBySalesOrderId(id);
    }
    public SalesOrder getSalesOrderByDate(Date date) {
        return salesOrderRepository.findByDate(date);
    }
    public SalesOrder getSalesOrderByPaymentStatus(PaymentStatus paymentStatus) {
        return salesOrderRepository.findByPaymentStatus(paymentStatus);
    }
    public List<SalesOrder> getSalesOrdersByCustomerId(String customer) {
        return salesOrderRepository.findByCustomer(customer);
    }
}