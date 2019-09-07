package com.eins.book.store.service.Impl;

import com.eins.book.store.dao.*;
import com.eins.book.store.entity.Book;
import com.eins.book.store.entity.ShippingAddress;
import com.eins.book.store.entity.UserOrder;
import com.eins.book.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServicelmpl implements OrderService {
    @Autowired
    private UserMapper userMapper;


    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private BookToCartItemMapper bookToCartItemMapper;

    @Autowired
    private UserShippingMapper userShippingMapper;

    @Autowired
    private BillingAddressMapper billingAddressMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private ShippingAddressMapper shippingAddressMapper;

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private BookMapper bookMapper;

    /*获得表userOrder的大小*/
    @Override
    public Long getUserOrderLastId() {
        Example example = new Example(UserOrder.class);
        example.createCriteria().getAllCriteria();
        List<UserOrder> userOrders = userOrderMapper.selectAll();
        Long id = 0l;
        for (UserOrder userOrder : userOrders) {
            if(userOrder.getId() > id) {
                id = userOrder.getId();
            }
        }
        return id;
    }

    /*获得*/
    public List<Long> getUserOrderIdsByUserId(Long userId) {
        Example example = new Example(UserOrder.class);
        example.createCriteria().andEqualTo("userId", userId);
        List<UserOrder> userOrders = userOrderMapper.selectByExample(example);
        List<Long> res = new ArrayList<Long>();
        for (UserOrder userOrder : userOrders) {
            res.add(userOrder.getId());
        }
        return res;

    }

    /**/
    @Override
    public UserOrder getUserOrderByUserOrderId(Long userOrderId) {
        Example example = new Example(UserOrder.class);
        example.createCriteria().andEqualTo("id", userOrderId);
        UserOrder userOrder = userOrderMapper.selectOneByExample(example);
        return userOrder;
    }

    /*插入userOrder*/
    @Override
    public void insertUserOrder(UserOrder userOrder) {
        userOrderMapper.insert(userOrder);
    }

}
