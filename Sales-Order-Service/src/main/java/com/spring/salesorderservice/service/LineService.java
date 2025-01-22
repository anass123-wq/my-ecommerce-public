package com.spring.salesorderservice.service;

import com.spring.salesorderservice.config.FeignClientSupplierService;
import com.spring.salesorderservice.config.FeignPaiement;
import com.spring.salesorderservice.dto.SalesLineDto;
import com.spring.salesorderservice.model.SalesLine;
import com.spring.salesorderservice.model.SalesOrder;
import com.spring.salesorderservice.repository.SalesLineRepository;
import com.spring.salesorderservice.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineService {
    @Autowired
    FeignClientSupplierService feignClientSupplierService;
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    private SalesLineRepository salesLineRepository;
    @Autowired
    private SalesOrderService salesOrderService;
    @Autowired
    private FeignPaiement feignPaiement;
    public SalesLine deleteSalesLine(Long id) {
        SalesLine salesLine = salesLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SalesLine not found"));
        SalesOrder order = salesOrderService.getSalesOrderBySalesLineId(id);
        order.setTotalAmount(order.getTotalAmount()-salesLine.getPrice()*salesLine.getQuantity());
        feignPaiement.updatePayment(order.getTotalAmount());
        salesLineRepository.delete(salesLine);
        return salesLine;
    }
    public SalesLine updateSalesLine(Long id, SalesLineDto salesLineDto) {
        SalesLine salesLine = salesLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SalesLine not found"));

        SalesOrder order = salesLine.getSalesOrder();
        double oldTotal = salesLine.getPrice() * salesLine.getQuantity();
        double newTotal = salesLineDto.getPrice() * salesLineDto.getQuantity();

        order.setTotalAmount(order.getTotalAmount() - oldTotal + newTotal);
        feignPaiement.updatePayment(order.getTotalAmount());
        salesLine.setProductId(salesLineDto.getProductId());
        salesLine.setQuantity(salesLineDto.getQuantity());
        salesLine.setPrice(salesLineDto.getPrice());

        salesLineRepository.save(salesLine);
        salesOrderRepository.save(order);

        return salesLine;
    }
    public List<SalesLine> getSalesLines(){
        return salesLineRepository.findAll();
    }


}
