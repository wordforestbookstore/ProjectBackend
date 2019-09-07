package com.eins.book.store.service;

import com.eins.book.store.entity.BillingAddress;
import com.eins.book.store.entity.UserBilling;
import com.eins.book.store.entity.UserPayment;

import java.util.List;

public interface BillingService {
    /*userPayment*/
    public boolean checkCardExist(Long userid);
    public boolean checkCardNameAndUserIdExist(String cardName, Long userId);
    public boolean checkCardNumberAndUserIdExist(String cardNumber, Long userId);
    public void insertUserPayment(UserPayment userPayment);
    public void updateUserPayment(UserPayment userPayment);
    public Long getUserPaymentIdByUserIdAndCardName(Long userId, String cardName);
    public Long getUserPaymentIdByUserIdAndCardNumber(Long userId, String CardNumber);
    public List<Long> getUserPaymentIdsByUserId(Long userId);
    public UserPayment getUserPaymentByUserPaymentId(Long userPaymentId);
    public boolean getDefaultByUserPaymentId(Long userPaymentId);
    public Long getUserPaymentIdByDefaultTrue(Long userId);
    public void delUserPayment(UserPayment userPayment);

    /*userBilling*/
    public void insertUserBilling(UserBilling userBilling);
    public void updateUserBilling(UserBilling userBilling);
    public Long getUserBillingIdByUserPaymentId(Long userPaymentId);
    public UserBilling getUserBillingByUserBillingId(Long userBillingId);
    public void delUserBilling(UserBilling userBilling);

    /*BillingAddress*/
    public Long getBillingAddressIdByOrderId(Long orderId);
    public Long getBillingAddressIdByOrderIdNull();
    public void insertBillingAddress(BillingAddress billingAddress);
    public BillingAddress getBillingAdressById(Long id);
    public void updateBillingAddress(BillingAddress billingAddress);
}
