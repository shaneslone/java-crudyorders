package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "paymentservices")
public class PaymentServicesImpl implements PaymentServices {
    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    @Override
    public Payment save(Payment payment){
        return paymentRepository.save((payment));
    }
}
