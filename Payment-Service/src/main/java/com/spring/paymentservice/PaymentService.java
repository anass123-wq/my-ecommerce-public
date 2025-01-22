package com.spring.paymentservice;

import com.spring.paymentservice.DTO.PaymentDTO;
import com.spring.paymentservice.config.FeignPurch;
import com.spring.paymentservice.config.FeignSales;
import com.spring.paymentservice.config.FeignSupplierClient;
import com.spring.paymentservice.model.DataPaidDate;
import com.spring.paymentservice.model.Payment;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.spring.paymentservice.model.Status.*;

@Service
public class PaymentService {



        @Autowired
        private PaymentRepository paymentRepository;
        @Autowired
        private DataPaidDateRepository dateRepository;
        @Autowired
        private FeignPurch feignPurch;
        @Autowired
        private FeignSales feignSales;
        @Autowired
        private FeignSupplierClient feignSupplierClient;

        @Transactional
        public Payment addPayment(PaymentDTO payment) {
            Payment newPayment = new Payment();
            newPayment.setOrderId(payment.getOrderId());
            newPayment.setAmountPaid(payment.getAmountPaid());
            newPayment.setOrdreType(payment.getOrdreType());
            newPayment.setTotalAmount(payment.getTotalAmount());
            newPayment.setNameSupplierClient(payment.getNameSupplierClient());
            if(newPayment.getAmountPaid()!=0){
                DataPaidDate dataPaidDate = new DataPaidDate();
                dataPaidDate.setAmountPaid(newPayment.getAmountPaid());
                dataPaidDate.setDate(new Date());
                dataPaidDate.setStatus(DEBUT);
                if(newPayment.getId()!= null) dataPaidDate.setPaymentId(newPayment.getId());
                dateRepository.save(dataPaidDate);
                newPayment.getPaidDates().add(dataPaidDate);
            }
            return paymentRepository.save(newPayment);
        }

        public Payment UpdatePayOrder(Long OrderId, double amount,String status) {
            if(status=="PAID"){
                return null;
            }
            Optional<Payment> payment = Optional.ofNullable(paymentRepository.findPaymentByOrderId(OrderId));
            if (payment.isPresent()){
                payment.get().setAmountPaid(payment.get().getAmountPaid()+amount);
                 if (payment.get().getTotalAmount()<=payment.get().getAmountPaid()) {
                     if (payment.get().getOrdreType() == "sales"){
                         feignPurch.updateStatus(Math.toIntExact(OrderId),"PAID");
                     } else if (payment.get().getOrdreType() == "purchases"){
                         feignSales.updateStatus(Math.toIntExact(OrderId),"PAID");
                     }
                }else {
                     if (payment.get().getOrdreType() == "sales"){
                         feignPurch.updateStatus(Math.toIntExact(OrderId),"PARTPAID");
                         feignSupplierClient.updateBorrowing(payment.get().getNameSupplierClient(),payment.get().getAmountPaid(),"sales");
                     } else if (payment.get().getOrdreType() == "purchases"){
                         feignSales.updateStatus(Math.toIntExact(OrderId),"PARTPAID");
                         feignSupplierClient.updateBorrowing(payment.get().getNameSupplierClient(),payment.get().getAmountPaid(),"purchases");
                     }
                 }
                if(payment.get().getAmountPaid()!=0){
                    DataPaidDate dataPaidDate = new DataPaidDate();
                    dataPaidDate.setAmountPaid(payment.get().getAmountPaid());
                    dataPaidDate.setDate(new Date());
                    if (payment.get().getTotalAmount()==payment.get().getAmountPaid()) dataPaidDate.setStatus(FIN);
                    else dataPaidDate.setStatus(DANS);
                    if(payment.get().getId()!= null) dataPaidDate.setPaymentId(payment.get().getId());
                    dateRepository.save(dataPaidDate);
                    payment.get().getPaidDates().add(dataPaidDate);
                }
            }
            return paymentRepository.save(payment.get());
        }
         public Payment getPaymentId(int id) {
            Optional<Payment> payment = Optional.ofNullable(paymentRepository.findPaymentByOrderId((long) id));
            if (payment.isPresent()){
                return payment.get();
            }
            return null;
        }
        public List<Payment> getAllPayments() {
            return paymentRepository.findAll();
        }

    }
