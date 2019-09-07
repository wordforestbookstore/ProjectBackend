package com.eins.book.store.service;

import com.eins.book.store.entity.Payment;

public interface PaymentService {
    public Long getPaymentIdByOrderId(Long OrderId);
    public Long getPaymentIdByOrderIdNull();
    public void insertPayment(Payment payment);
    public Payment getPaymentById(Long id);
    public void updatePayment(Payment payment);
}
