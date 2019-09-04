package com.eins.book.store.service.Impl;

import com.eins.book.store.dao.UserBillingMapper;
import com.eins.book.store.dao.UserPaymentMapper;
import com.eins.book.store.entity.Book;
import com.eins.book.store.entity.UserBilling;
import com.eins.book.store.entity.UserPayment;
import com.eins.book.store.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BillingServicelmpl implements BillingService {
    @Autowired
    private UserPaymentMapper userPaymentMapper;


    @Autowired
    private UserBillingMapper userBillingMapper;

    /*检查该user中是否存在Card(payment)*/
    @Override
    public boolean checkCardExist(Long userid) {
        Example example = new Example(UserPayment.class);
        example.createCriteria().andEqualTo("userId", userid);
        int num = userPaymentMapper.selectCountByExample(example);
        if(num == 0) {
            return false;
        }
        return true;
    }
    /*检查cardName是否已经存在*/
    @Override
    public boolean checkCardNameAndUserIdExist(String cardName, Long userid) {
        Example example = new Example(UserPayment.class);
        example.createCriteria().andEqualTo("cardName", cardName).andEqualTo("userId", userid);
        UserPayment userPayment = userPaymentMapper.selectOneByExample(example);
        if(userPayment == null) {
            System.out.println("该cardName: " + cardName + "在该id: "+ userid + "不存在");
            return false;
        }
        System.out.println("该cardName: " + cardName + "在该id: "+ userid + "不存在");
        return true;
    }

    public boolean checkCardNumberAndUserIdExist(String cardNumber, Long userId) {
        Example example = new Example(UserPayment.class);
        example.createCriteria().andEqualTo("cardNumber", cardNumber).andEqualTo("userId", userId);
        UserPayment userPayment = userPaymentMapper.selectOneByExample(example);
        if(userPayment == null) {
            System.out.println("该cardNumber: " + cardNumber + "在该id: "+ userId + "不存在");
            return false;
        }
        System.out.println("该cardNumber: " + cardNumber + "在该id: "+ userId + "不存在");
        return true;
    }

    /*插入user_payment*/
    @Override
    public void insertUserPayment(UserPayment userPayment) {
        userPaymentMapper.insert(userPayment);
    }
    /*通过id更新user_paymen*/
    @Override
    public void updateUserPayment(UserPayment userPayment) {
        userPaymentMapper.updateByPrimaryKeySelective(userPayment);
    }

    /*通过userid获得user_paymentid*/
    @Override
    public Long getUserPaymentIdByCardNameAndUserId(String cardName, Long userId) {
        Example example = new Example(UserPayment.class);
        example.createCriteria().andEqualTo("cardName", cardName).andEqualTo("userId", userId);
        UserPayment userPayment = userPaymentMapper.selectOneByExample(example);
        if(userPayment == null) {
            System.out.println("通过cardName: " + cardName +" 和id: "+ userId + " 找到一个空的userpayment: "+ userPayment);
            return null;
        }
        System.out.println("通过cardName: " + cardName +" 和id: "+ userId + " 找到一个userpaymentId: "+ userPayment.getId());
        return userPayment.getId();
    }
    /*通过userId获得user_paymentIds*/
    @Override
    public List<Long> getUserPaymentIdsByUserId(Long userId) {
        Example example = new Example(UserPayment.class);
        example.createCriteria().andEqualTo("userId", userId);
        List<UserPayment> userPayments = userPaymentMapper.selectByExample(example);
        List<Long> userPaymentIds = new ArrayList<Long>();
        for (UserPayment userPayment : userPayments) {
            userPaymentIds.add(userPayment.getId());
        }
        return userPaymentIds;
    }

    /*通过userPaymentId获得userPayment*/
    @Override
    public UserPayment getUserPaymentByUserPaymentId(Long userPaymentId) {
        Example example = new Example(UserPayment.class);
        example.createCriteria().andEqualTo("id", userPaymentId);
        UserPayment userPayment = userPaymentMapper.selectOneByExample(example);
        if(userPayment == null) {
            System.out.println("通过userPaymentId没有找到userPayment");
        }
        return userPayment;
    }

    /*通过userPatmentId获得其Default*/
    @Override
    public boolean getDefaultByUserPaymentId(Long userPaymentId) {
        Example example = new Example(UserPayment.class);
        example.createCriteria().andEqualTo("id", userPaymentId);
        UserPayment userPayment = userPaymentMapper.selectOneByExample(example);
        return userPayment.getDefaultPayment();
    }

    /*找到Default为true的userPayment*/
    public Long getUserPaymentIdByDefaultTrue() {
        boolean flag = true;
        Example example = new Example(UserPayment.class);
        example.createCriteria().andEqualTo("defaultPayment", flag);
        UserPayment userPayment = userPaymentMapper.selectOneByExample(example);
        if(userPayment == null) {
            return 0l;
        }
        return userPayment.getId();
    }

    /*删除userPayment*/
    public void delUserPayment(UserPayment userPayment) {
        userPaymentMapper.deleteByPrimaryKey(userPayment);
    }
    /*插入userBilling*/
    @Override
    public void insertUserBilling(UserBilling userBilling) {
        userBillingMapper.insert(userBilling);
    }

    /*通过id updateUserBilling*/
    public void updateUserBilling(UserBilling userBilling) {
        userBillingMapper.updateByPrimaryKeySelective(userBilling);
    }

    /*通过userPaymentId获得UserBillingId*/
    public Long getUserBillingIdByUserPaymentId(Long userPaymentId) {
        Example example = new Example(UserBilling.class);
        example.createCriteria().andEqualTo("userPaymentId", userPaymentId);
        UserBilling userBilling = userBillingMapper.selectOneByExample(example);
        return userBilling.getId();
    }

    /*通过userBillingId获得userBilling*/
    public UserBilling getUserBillingByUserBillingId(Long userBillingId) {
        Example example = new Example(UserBilling.class);
        example.createCriteria().andEqualTo("id", userBillingId);
        UserBilling userBilling = userBillingMapper.selectOneByExample(example);
        return userBilling;
    }

    /*删除userBilling*/
    public void delUserBilling(UserBilling userBilling) {
        userBillingMapper.deleteByPrimaryKey(userBilling);
    }
}
