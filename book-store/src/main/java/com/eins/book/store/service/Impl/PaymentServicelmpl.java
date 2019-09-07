package com.eins.book.store.service.Impl;

import com.eins.book.store.dao.PaymentMapper;
import com.eins.book.store.entity.BillingAddress;
import com.eins.book.store.entity.Payment;
import com.eins.book.store.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PaymentServicelmpl implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;

    /*通过OrderId找到paymenId*/
    @Override
    public Long getPaymentIdByOrderId(Long orderId) {
        Example example = new Example(Payment.class);
        example.createCriteria().andEqualTo("orderId", orderId);
        Payment payment = paymentMapper.selectOneByExample(example);
        if(payment == null) {
            return 0l;
        }
        return payment.getId();
    }

    @Override
    /*获得orderId为空的billingaddressId*/
    public Long getPaymentIdByOrderIdNull() {
        Example example = new Example(BillingAddress.class);
        List<Payment> payments = paymentMapper.selectAll();
        if(payments.size() == 0) {
            return 0l;
        }
        Long res = 0l;
        for (Payment payment : payments) {
            if(payment.getOrderId() == null) {
                res = payment.getId();
            }
        }
        return res;
    }
    @Override
    public void insertPayment(Payment payment) {
        paymentMapper.insert(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        Example example = new Example(Payment.class);
        example.createCriteria().andEqualTo("id", id);
        Payment payment = paymentMapper.selectOneByExample(example);
        if(payment == null) {
            return null;
        }
        return payment;
    }

    @Override
    public void updatePayment(Payment payment) {
        paymentMapper.updateByPrimaryKeySelective(payment);
    }
}
