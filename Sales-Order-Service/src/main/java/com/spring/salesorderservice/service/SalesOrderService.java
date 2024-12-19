package com.spring.salesorderservice.service;

import com.spring.salesorderservice.config.FeignClientSupplierService;
import com.spring.salesorderservice.config.FeignProductService;
import com.spring.salesorderservice.dto.SalesLineDto;
import com.spring.salesorderservice.dto.SalesOrderDto;

import com.spring.salesorderservice.model.PaymentStatus;
import com.spring.salesorderservice.model.SalesLine;
import com.spring.salesorderservice.model.SalesOrder;
import com.spring.salesorderservice.repository.SalesLineRepository;
import com.spring.salesorderservice.repository.SalesOrderRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
        }
        feignClientSupplierService.updateTotalOrder(salesOrder.getCustomer(),totalAmount);
        salesOrder.setTotalAmount(totalAmount);

        salesOrder.setPaymentStatus(salesOrderDto.getPaymentStatus());
        return salesOrderRepository.save(salesOrder);

    }
    public SalesOrder getSalesOrderById(Long id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales Order not found with id: " + id));
    }
    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }
    public List<SalesLine> getSalesLines(){
        return salesLineRepository.findAll();
    }
    public List<SalesOrder> getSalesOrdersByCustomerId(String customer) {
        return salesOrderRepository.findByCustomer(customer);
    }

    public void deleteSalesOrder(Long id) {
        salesOrderRepository.deleteById(id);
    }
    public SalesOrder getSalesOrderByDate(Date date) {
        return salesOrderRepository.findByDate(date);
    }
    public Optional<SalesOrder> getSalesOrderByTotalAmount(Double totalAmount) {
        return salesOrderRepository.findByTotalAmount(totalAmount);
    }
    public SalesOrder getSalesOrderByPaymentStatus(PaymentStatus paymentStatus) {
        return salesOrderRepository.findByPaymentStatus(paymentStatus);
    }
    @Transactional
    public SalesOrder updateSalesInvoice( Long id,  SalesOrderDto salesOrderDto){
        Optional<SalesOrder> salesOrder = salesOrderRepository.findById(id);
        if (salesOrder.isPresent()) {
            salesOrder.get().setCustomer(salesOrderDto.getCustomer());
            salesOrder.get().setTotalAmount(salesOrderDto.getTotalAmount());
            salesOrder.get().setPaymentStatus(salesOrderDto.getPaymentStatus());
            salesOrder.get().setDate(salesOrderDto.getDate());
            salesOrderRepository.save(salesOrder.get());
        }
        return salesOrder.get();
    }
    public List<SalesOrder> searchSalesInvoices(@Param("query") String query) {
        Double totalAmount = null;
        try {
            totalAmount = Double.parseDouble(query);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return salesOrderRepository.searchSalesOrder(totalAmount, query);
    }
    public SalesLine updateSalesLine(Long id, SalesLineDto salesLineDto) {
        SalesLine salesLine = salesLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SalesLine not found"));

        SalesOrder order = salesLine.getSalesOrder();
        double oldTotal = salesLine.getPrice() * salesLine.getQuantity();
        double newTotal = salesLineDto.getPrice() * salesLineDto.getQuantity();

        order.setTotalAmount(order.getTotalAmount() - oldTotal + newTotal);

        salesLine.setProductId(salesLineDto.getProductId());
        salesLine.setQuantity(salesLineDto.getQuantity());
        salesLine.setPrice(salesLineDto.getPrice());

        salesLineRepository.save(salesLine);
        salesOrderRepository.save(order);

        return salesLine;
    }

    public SalesLine deleteSalesLine(Long id) {
        SalesLine salesLine = salesLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SalesLine not found"));

        salesLineRepository.delete(salesLine);
        return salesLine;
    }

    public SalesOrder getSalesOrderBySalesLineId(Long id) {
        SalesLine salesLine = salesLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SalesLine not found"));

        return salesOrderRepository.findBySalesLinesContaining(salesLine);
    }
}
/*


    public List<SalesLine> getSalesLinesBySalesOrderId(Long id) {
        return salesLineRepository.findSalesLineBySalesOrderId(id);
    }
    public List<SalesLine> getSalesLinesByProductId(Long id) {
        return salesLineRepository.findSalesLinesByProductId(id);
    }*/

// SupplierClientDto supplierClientDto = feignClientSupplierService.getSupplierClientByName(name);
//  SupplierClient supplierClient = SupplierClientMapper.toEntity(supplierClientDto);

 /*Long supplierId
        // ربط المورد بالفاتورة
        Supplier supplierEntity = new Supplier();
        supplierEntity.setId(supplier.getId());
        supplierEntity.setName(supplier.getName());
        supplierEntity.setEmail(supplier.getEmail());
        supplierEntity.setPhoneNumber(supplier.getPhoneNumber());

        order.setSupplier(supplierEntity);*/