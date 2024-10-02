package com.spring.salesorderservice.service;

import com.spring.salesorderservice.Mapper.SupplierClientMapper;
import com.spring.salesorderservice.config.FeignClientSupplierService;
import com.spring.salesorderservice.config.FeignProductService;
import com.spring.salesorderservice.dto.SalesLineDto;
import com.spring.salesorderservice.dto.SalesOrderDto;
import com.spring.salesorderservice.dto.SupplierClientDto;
import com.spring.salesorderservice.model.SalesLine;
import com.spring.salesorderservice.model.SalesOrder;
import com.spring.salesorderservice.model.SupplierClient;
import com.spring.salesorderservice.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalesOrderService {
    @Autowired
    FeignClientSupplierService feignClientSupplierService;
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private FeignProductService feignProductService;

    @Transactional
    public SalesOrder createSalesOrder(SalesOrderDto salesOrderDto ,String name) {

         SupplierClientDto supplierClientDto = feignClientSupplierService.getSupplierClientByName(name);
          SupplierClient supplierClient = SupplierClientMapper.toEntity(supplierClientDto);

 /*Long supplierId
        // ربط المورد بالفاتورة
        Supplier supplierEntity = new Supplier();
        supplierEntity.setId(supplier.getId());
        supplierEntity.setName(supplier.getName());
        supplierEntity.setEmail(supplier.getEmail());
        supplierEntity.setPhoneNumber(supplier.getPhoneNumber());

        order.setSupplier(supplierEntity);*/
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCustomer(supplierClient);
        double totalAmount = 0;

        for (SalesLineDto line : salesOrderDto.getSalesLines()) {
            SalesLine salesLine = new SalesLine();
            salesLine.setProductId(line.getProductId());
            salesLine.setQuantity(line.getQuantity());
            salesLine.setPrice(line.getPrice());

            // Reduce product stock
            feignProductService.reduceStock(line.getProductId(), line.getQuantity());

            salesLine.setSalesOrder(salesOrder);
            salesOrder.getSalesLines().add(salesLine);
            totalAmount += line.getPrice() * line.getQuantity();
        }

        salesOrder.setTotalAmount(totalAmount);
        salesOrder.setPaid(false);
        return salesOrderRepository.save(salesOrder);
    }

    public SalesOrder getSalesOrderById(Long id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales Order not found with id: " + id));
    }
}

