package com.eins.book.store.service;

import com.eins.book.store.entity.UserOrder;

import java.util.List;

public interface OrderService {
    public Long getUserOrderLastId();
    public List<Long> getUserOrderIdsByUserId(Long UserId);
    public UserOrder getUserOrderByUserOrderId(Long OrderId);

    public void insertUserOrder(UserOrder userOrder);

}
