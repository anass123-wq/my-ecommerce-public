package com.spring.salesorderservice.service;

import com.spring.salesorderservice.config.FeignPaiement;
import com.spring.salesorderservice.config.FeignProductService;
import com.spring.salesorderservice.model.LineReturn;
import com.spring.salesorderservice.model.SalesOrder;
import com.spring.salesorderservice.repository.LineReturnRepository;
import com.spring.salesorderservice.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReturnService {
    @Autowired
    private LineReturnRepository lineReturnRepository;
    @Autowired
    private LineService lineService;
    @Autowired
    private FeignPaiement feignPaiement;
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    private FeignProductService feignProductService;
    public List<LineReturn> getAllLineReturn() {
        return lineReturnRepository.findAll();
    }
    public LineReturn getLineReturnById(int id) {
        return lineReturnRepository.findById(id).get();
    }
    public LineReturn addLineReturn(LineReturn lineReturn) {
        lineService.deleteSalesLine(lineReturn.getOrderId());
        return lineReturnRepository.save(lineReturn);
    }
    public LineReturn updateLineReturn(LineReturn lineReturn) {
        LineReturn lineReturn1=lineReturnRepository.getReferenceById(Math.toIntExact(lineReturn.getId()));
        if(lineReturn1!=null&&lineReturn1.getProductId()==lineReturn.getProductId()){
            double deff= lineReturn1.getQuantity()*lineReturn1.getPrice()-lineReturn.getQuantity()*lineReturn1.getPrice();
            SalesOrder salesOrder=lineReturn1.getSalesOrder();
            if (deff<0) {
                salesOrder.setTotalAmount(salesOrder.getTotalAmount()-Math.abs(deff));
            }
            if (deff>0) {
               salesOrder.setTotalAmount(salesOrder.getTotalAmount()+deff);
            }
            int deffQuantity= lineReturn1.getQuantity()-lineReturn.getQuantity();
            if (deffQuantity>0) {
                feignProductService.reduceStock(lineReturn1.getProductId(),deffQuantity);
                feignProductService.updateDate(lineReturn1.getProductId(),new Date());
            }else {
                feignProductService.addStock(lineReturn1.getProductId(),Math.abs(deffQuantity));
                feignProductService.updateDate(lineReturn1.getProductId(),new Date());
            }
            salesOrderRepository.save(salesOrder);
            feignPaiement.updatePayment(salesOrder.getId(),salesOrder.getTotalAmount());
        }
        return lineReturnRepository.save(lineReturn);
    }
    public void deleteLineReturnById(int id) {
        lineReturnRepository.deleteById(id);;
    }
}
