package com.spring.paymentservice;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {



        @Autowired
        private PaymentRepository paymentRepository;



        @Transactional
        public Payment payOrder(Long OrderId, double amount) {
            boolean test = true;
            if(test==false){
                return null;
            }
            Optional<Payment> payment = Optional.ofNullable(paymentRepository.findPaymentByOrderId(OrderId));
            if (payment.isPresent()){
                payment.get().setAmountPaid(payment.get().getAmountPaid()+amount);
                 if (payment.get().getTotalAmount()==payment.get().getAmountPaid()) {
                     test=false;
                     if (payment.get().getOrdreType() == "sales"){

                     } else if (payment.get().getOrdreType() == ""){

                     }

                }
            }

            return paymentRepository.save(payment);
        }

    }
