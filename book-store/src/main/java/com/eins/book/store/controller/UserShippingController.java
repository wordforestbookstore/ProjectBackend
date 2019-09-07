package com.eins.book.store.controller;

import com.eins.book.store.commons.ConstantUtils;
import com.eins.book.store.commons.CookieUtils;
import com.eins.book.store.entity.User;
import com.eins.book.store.entity.UserBilling;
import com.eins.book.store.entity.UserPayment;
import com.eins.book.store.entity.UserShipping;
import com.eins.book.store.service.ShippingService;
import com.eins.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserShippingController {
    @Autowired
    private UserService userService;

    @Autowired
    private ShippingService shippingService;

    @RequestMapping(value = "/Shipping", method = RequestMethod.POST)
    public ResponseEntity addShipping(@RequestBody Map<String, String> mp, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            /*Insert user_shipping(user_id, user_shipping_city, user_shipping_country, user_shipping_default,
             * user_shipping_name, user_shipping_state, user_shipping_street1, user_shipping_street2
             * user_shipping_zipcode)
             * */
            UserShipping userShipping = new UserShipping();
            userShipping.setUserId(userId);
            if(userShipping.getUserId() == null || userShipping.getUserId() == 0l) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your userid is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingCity(mp.get("shippingcity"));
            if(userShipping.getUserShippingCity() == null || userShipping.getUserShippingCity() == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your shippingcity is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingName(mp.get("shippingname"));
            if(userShipping.getUserShippingName() == null || userShipping.getUserShippingName() == "" ) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your shippingname is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingState(mp.get("shippingstate"));
            if(userShipping.getUserShippingState() == "" || userShipping.getUserShippingState() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your shippingstate is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingStreet1(mp.get("shippingstreet1"));
            if(userShipping.getUserShippingStreet1() == "" || userShipping.getUserShippingStreet1() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your shippingstreet1 is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingStreet2(mp.get("shippingstreet2"));
            if(userShipping.getUserShippingStreet2() == "" || userShipping.getUserShippingStreet2() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your shippingstreet2 is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingZipcode(mp.get("zipcode"));
            if(userShipping.getUserShippingZipcode() == "" || userShipping.getUserShippingZipcode() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your zipcode is null", HttpStatus.BAD_REQUEST);
            }
            if(!shippingService.checkShippingExistByUserId(userId)) {
                userShipping.setUserShippingDefault(true);
            }
            else {
                userShipping.setUserShippingDefault(false);
            }
            if(shippingService.checkShippingAddressExist(userShipping.getId(), userShipping.getUserShippingStreet1(), userShipping.getUserShippingStreet2(), userShipping.getUserShippingCity(), userShipping.getUserShippingName())) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("This address is exist", HttpStatus.BAD_REQUEST);
            }
            shippingService.insertUserShipping(userShipping);
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Save successfully!", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/Shipping", method = RequestMethod.GET)
    public ResponseEntity getCreditCard(String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            //通过userId得到一些user_shipping
            List<Long> userShippingIds = shippingService.getUserShippingIdsByUserId(userId);
            List<Map<String, String>> mps =  new ArrayList<Map<String, String>>();
            for (Long userShippingId : userShippingIds) {
                Map<String, String> mp1 = new HashMap<String, String>();
                UserShipping userShipping = shippingService.getUserShippingByUserShippingId(userShippingId);
                mp1.put("id", userShipping.getId().toString());
                mp1.put("default", userShipping.getUserShippingDefault().toString());
                mp1.put("shippingstreet1", userShipping.getUserShippingStreet1());
                mp1.put("shippingstreet2", userShipping.getUserShippingStreet2());
                mp1.put("shippingstate", userShipping.getUserShippingState());
                mp1.put("shippingcity", userShipping.getUserShippingCity());
                mp1.put("shippingname", userShipping.getUserShippingName());
                mp1.put("zipcode", userShipping.getUserShippingZipcode());
                mp1.put("userid", userShipping.getUserId().toString());
                mps.add(mp1);
            }
            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(mps, HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }
    @RequestMapping(value = "/Shipping/{id}", method = RequestMethod.GET)
    public ResponseEntity updateShipping(@PathVariable("id") Long userShippingId, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            Map<String, String> mp = new HashMap<>();
            UserShipping userShipping = shippingService.getUserShippingByUserShippingId(userShippingId);
            mp.put("userid", userId.toString());
            mp.put("shippingcity", userShipping.getUserShippingCity());
            mp.put("shippingstreet1", userShipping.getUserShippingStreet1());
            mp.put("shippingstreet2", userShipping.getUserShippingStreet2());
            mp.put("shippingstate", userShipping.getUserShippingState());
            mp.put("shippingname", userShipping.getUserShippingName());
            mp.put("zipcode", userShipping.getUserShippingZipcode());
            mp.put("id", userShipping.getId().toString());
            mp.put("default", userShipping.getUserShippingDefault().toString());
            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(mp, HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }
    @RequestMapping(value = "/Shipping/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateShipping(@PathVariable("id") Long userShippingId, @RequestBody Map<String, String> mp, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            /*Insert user_shipping(user_id, user_shipping_city, user_shipping_country, user_shipping_default,
             * user_shipping_name, user_shipping_state, user_shipping_street1, user_shipping_street2
             * user_shipping_zipcode)
             * */
            UserShipping userShipping = shippingService.getUserShippingByUserShippingId(userShippingId);
            userShipping.setUserId(userId);
            if(userShipping.getUserId() == null || userShipping.getUserId() == 0l) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your userid is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingCity(mp.get("shippingcity"));
            if(userShipping.getUserShippingCity() == null || userShipping.getUserShippingCity() == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your shippingcity is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingName(mp.get("shippingname"));
            if(userShipping.getUserShippingName() == null || userShipping.getUserShippingName() == "" ) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your shippingname is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingState(mp.get("shippingstate"));
            if(userShipping.getUserShippingState() == "" || userShipping.getUserShippingState() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your shippingstate is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingStreet1(mp.get("shippingstreet1"));
            if(userShipping.getUserShippingStreet1() == "" || userShipping.getUserShippingStreet1() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your shippingstreet1 is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingStreet2(mp.get("shippingstreet2"));
            if(userShipping.getUserShippingStreet2() == "" || userShipping.getUserShippingStreet2() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your shippingstreet2 is null", HttpStatus.BAD_REQUEST);
            }

            userShipping.setUserShippingZipcode(mp.get("zipcode"));
            if(userShipping.getUserShippingZipcode() == "" || userShipping.getUserShippingZipcode() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Your zipcode is null", HttpStatus.BAD_REQUEST);
            }
            if(shippingService.checkShippingAddressExist(userShipping.getId(), userShipping.getUserShippingStreet1(), userShipping.getUserShippingStreet2(), userShipping.getUserShippingCity(), userShipping.getUserShippingName())) {
                if(shippingService.getUserShippingIdByUserIdAndStreetAndCityAndName(userShipping.getUserId(), userShipping.getUserShippingStreet1(), userShipping.getUserShippingStreet2(), userShipping.getUserShippingCity(), userShipping.getUserShippingName()) == userShippingId) {
                    httpServletResponse.setContentType("text/plain");
                    return new ResponseEntity("This address is exist", HttpStatus.BAD_REQUEST);
                }
            }

            shippingService.updateUserShipping(userShipping);
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Save successfully!", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/ShippingDefault/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateShippingDefault(@PathVariable("id") Long userShippingId, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            Long oldUserShippingId = shippingService.getUserShippingIdByDefaultTrue(userId);
            UserShipping oldUserShipping = shippingService.getUserShippingByUserShippingId(oldUserShippingId);
            oldUserShipping.setUserShippingDefault(false);
            UserShipping newUserShipping = shippingService.getUserShippingByUserShippingId(userShippingId);
            newUserShipping.setUserShippingDefault(true);
            shippingService.updateUserShipping(oldUserShipping);
            shippingService.updateUserShipping(newUserShipping);
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Update Default Successfully", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }
    @RequestMapping(value = "/Shipping/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletDefault(@PathVariable("id") Long userShippingId, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            UserShipping userShipping = shippingService.getUserShippingByUserShippingId(userShippingId);
            shippingService.delUserShipping(userShipping);
            Long oldUserShippingId = shippingService.getUserShippingIdByDefaultTrue(userId);
            if(oldUserShippingId == 0l) {
                List<Long> userShippingIds = shippingService.getUserShippingIdsByUserId(userId);
                if(userShippingIds.size() == 0) {
                    httpServletResponse.setContentType("text/plain");
                    return new ResponseEntity("Your Shipping is empty!", HttpStatus.OK);
                }
                Long tmpId = Long.MAX_VALUE;
                UserShipping tmpUserShipping = new UserShipping();
                for (Long shippingId : userShippingIds) {
                    if(shippingId < tmpId) {
                        tmpId = shippingId;
                        tmpUserShipping = shippingService.getUserShippingByUserShippingId(shippingId);
                        tmpUserShipping.setUserShippingDefault(true);
                    }
                }
                shippingService.updateUserShipping(tmpUserShipping);
            }
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Delete successfully", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }
}