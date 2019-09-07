package com.eins.book.store.service.Impl;

import com.eins.book.store.dao.ShippingAddressMapper;
import com.eins.book.store.dao.UserShippingMapper;
import com.eins.book.store.entity.BillingAddress;
import com.eins.book.store.entity.ShippingAddress;
import com.eins.book.store.entity.UserPayment;
import com.eins.book.store.entity.UserShipping;
import com.eins.book.store.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingServicelmpl implements ShippingService {
    @Autowired
    private UserShippingMapper userShippingMapper;

    @Autowired
    private ShippingAddressMapper shippingAddressMapper;

    /*检查该user中是否存在ShippingAddress*/
    @Override
    public boolean checkShippingExistByUserId(Long userid) {
        Example example = new Example(UserShipping.class);
        example.createCriteria().andEqualTo("userId", userid);
        int num = userShippingMapper.selectCountByExample(example);
        if(num == 0) {
            return false;
        }
        return true;
    }

    @Override
    /*获得orderId为空的shippingaddressId*/
    public Long getShippingAddressIdByOrderIdNull() {
        Example example = new Example(ShippingAddress.class);
        List<ShippingAddress> shippingAddresses = shippingAddressMapper.selectAll();
        if(shippingAddresses.size() == 0) {
            return 0l;
        }
        Long res = 0l;
        for (ShippingAddress shippingAddress : shippingAddresses) {
            if(shippingAddress.getOrderId() == null) {
                res = shippingAddress.getId();
                break;
            }
        }
        return res;
    }
    /*检查是否存在相同地址*/
    @Override
    public boolean checkShippingAddressExist(Long userId, String shippingstreet1, String shippingstreet2, String shippingcity, String shippingName) {
        Example example = new Example((UserShipping.class));
        example.createCriteria().andEqualTo("userShippingCity", shippingcity).andEqualTo("userShippingName", shippingName).andEqualTo("userId", userId).andEqualTo("userShippingStreet1", shippingstreet1).andEqualTo("userShippingStreet2", shippingstreet2);
        int num = userShippingMapper.selectCountByExample(example);
        if(num == 0) {
            return false;
        }
        return true;
    }

    /*插入userShipping*/
    @Override
    public void insertUserShipping(UserShipping userShipping) {
        userShippingMapper.insert(userShipping);
    }

    /*更新userShipping*/
    @Override
    public void updateUserShipping(UserShipping userShipping) {
        userShippingMapper.updateByPrimaryKeySelective(userShipping);
    }

    /*删除userShipping*/
    @Override
    public void delUserShipping(UserShipping userShipping) {
        userShippingMapper.deleteByPrimaryKey(userShipping);
    }
    /*通过userId获得user_shippingIds*/
    @Override
    public List<Long> getUserShippingIdsByUserId(Long userId) {
        Example example = new Example(UserShipping.class);
        example.createCriteria().andEqualTo("userId", userId);
        List<UserShipping> userShippings = userShippingMapper.selectByExample(example);
        List<Long> userShippingIds = new ArrayList<Long>();
        for (UserShipping userShipping : userShippings) {
            userShippingIds.add(userShipping.getId());
        }
        return userShippingIds;
    }

    /*通过user_shipping_id获得user——shipping*/
    @Override
    public UserShipping getUserShippingByUserShippingId(Long userShippingid) {
        Example example = new Example(UserShipping.class);
        example.createCriteria().andEqualTo("id", userShippingid);
        UserShipping userShipping = userShippingMapper.selectOneByExample(example);
        return userShipping;
    }

    /*通过userid，地址，name获得userShippingId*/
    @Override
    public Long getUserShippingIdByUserIdAndStreetAndCityAndName(Long userId, String shippingStreet1, String shippingStreet2, String shippingCity, String shippingName) {
        Example example = new Example((UserShipping.class));
        example.createCriteria().andEqualTo("userShippingCity", shippingCity).andEqualTo("userShippingName", shippingName).andEqualTo("userId", userId).andEqualTo("userShippingStreet1", shippingStreet1).andEqualTo("userShippingStreet2", shippingStreet2);
        UserShipping userShipping = userShippingMapper.selectOneByExample(example);
        return userShipping.getId();
    }

    /*找到默认地址id*/
    @Override
    public Long getUserShippingIdByDefaultTrue(Long userId) {
        boolean flag = true;
        Example example = new Example((UserShipping.class));
        example.createCriteria().andEqualTo("userShippingDefault", flag).andEqualTo("userId", userId);
        UserShipping userShipping = userShippingMapper.selectOneByExample(example);
        if(userShipping == null) {

            return 0l;
        }
        return userShipping.getId();
    }

    /*通过OrderId获得shippingAddressId*/
    public Long getShippingAddressIdByOrderId(Long orderId) {
        Example example = new Example(ShippingAddress.class);
        example.createCriteria().andEqualTo("orderId", orderId);
        ShippingAddress shippingAddress = shippingAddressMapper.selectOneByExample(example);
        if(shippingAddress == null) {
            return 0l;
        }
        return shippingAddress.getId();
    }

    /*插入shippingAddress*/
    public void insertShippingAddress(ShippingAddress shippingAddress) {
        shippingAddressMapper.insert(shippingAddress);
    }

    @Override
    public ShippingAddress getShippingAddressById(Long id) {
        Example example = new Example(ShippingAddress.class);
        example.createCriteria().andEqualTo("id", id);
        ShippingAddress shippingAddress = shippingAddressMapper.selectOneByExample(example);
        if(shippingAddress == null) {
            return null;
        }
        return shippingAddress;
    }

    @Override
    public void updateShippingAddress(ShippingAddress shippingAddress) {
        shippingAddressMapper.updateByPrimaryKeySelective(shippingAddress);
    }
}
