package com.spring.purchaseorderservice.service;

import com.spring.purchaseorderservice.Mapper.SupplierClientMapper;
import com.spring.purchaseorderservice.config.FeignClientSupplierService;
import com.spring.purchaseorderservice.config.FeignProductService;
import com.spring.purchaseorderservice.dto.PurchaseLineDto;
import com.spring.purchaseorderservice.dto.PurchaseOrderDto;
import com.spring.purchaseorderservice.dto.SupplierClientDto;
import com.spring.purchaseorderservice.model.PurchaseLine;
import com.spring.purchaseorderservice.model.PurchaseOrder;
import com.spring.purchaseorderservice.model.SupplierClient;
import com.spring.purchaseorderservice.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private FeignProductService feignProductService;

    @Autowired
    FeignClientSupplierService feignClientSupplierService;
/*
*
*
    public PurchaseOrder createPurchaseOrder(Long supplierId, List<PurchaseLineDto> purchaseLines) {
        // جلب بيانات المورد باستخدام FeignClient
        SupplierClientDto supplierClientDto = feignClientSupplierService.getSupplierClientById(supplierId);

        // تحويل DTO إلى كيان SupplierClient (بافتراض وجود تحويل)
        SupplierClient supplierClient = SupplierClientMapper.toEntity(supplierClientDto);

        // إنشاء أمر الشراء
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplier(supplierClient);
        purchaseOrder.setPurchaseLines(purchaseLines.stream()
                                .map(PurchaseLineMapper::toEntity)
                                .collect(Collectors.toList()));

        // حساب المبلغ الإجمالي
        double totalAmount = purchaseLines.stream()
                                          .mapToDouble(line -> line.getQuantity() * line.getUnitPrice())
                                          .sum();
        purchaseOrder.setTotalAmount(totalAmount);

        // حفظ أمر الشراء
        return purchaseOrderRepository.save(purchaseOrder);
    }
}

*  */
    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrderDto purchaseOrderDto ,String name) {
        SupplierClientDto supplierClientDto = feignClientSupplierService.getSupplierClientByName(name);
        SupplierClient supplierClient = SupplierClientMapper.toEntity(supplierClientDto);


        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplier(supplierClient);
        double totalAmount = 0;

        for (PurchaseLineDto line : purchaseOrderDto.getPurchaseLines()) {
            PurchaseLine purchaseLine = new PurchaseLine();
            purchaseLine.setProductId(line.getProductId());
            purchaseLine.setQuantity(line.getQuantity());
            purchaseLine.setPrice(line.getPrice());

            // Add product stock
            feignProductService.addStock(line.getProductId(), line.getQuantity());

            purchaseLine.setPurchaseOrder(purchaseOrder);
            purchaseOrder.getPurchaseLines().add(purchaseLine);
            totalAmount += line.getPrice() * line.getQuantity();
        }

        purchaseOrder.setTotalAmount(totalAmount);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found with id: " + id));
    }
}
