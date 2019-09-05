package com.eins.book.store.service;

import com.eins.book.store.entity.UserShipping;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ShippingService {
    public boolean checkShippingExistByUserId(Long userId);
    public boolean checkShippingAddressExist(Long userId, String shippingstreet1, String shippingstreet2, String shippingcity, String shippingName);
    public void insertUserShipping(UserShipping userShipping);
    public void updateUserShipping(UserShipping userShipping);
    public void delUserShipping(UserShipping userShipping);
    public List<Long> getUserShippingIdsByUserId(Long userId);
    public UserShipping getUserShippingByUserShippingId(Long userShippingid);
    public Long getUserShippingIdByUserIdAndStreetAndCityAndName(Long userId, String shippingStreet1, String shippingStreet2, String shippingCity, String shippingName);
    public Long getUserShippingIdByDefaultTrue();
}
