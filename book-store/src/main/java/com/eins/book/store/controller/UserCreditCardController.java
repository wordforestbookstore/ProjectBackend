package com.eins.book.store.controller;

import com.eins.book.store.commons.ConstantUtils;
import com.eins.book.store.commons.CookieUtils;
import com.eins.book.store.entity.User;
import com.eins.book.store.entity.UserBilling;
import com.eins.book.store.entity.UserPayment;
import com.eins.book.store.service.BillingService;
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
public class UserCreditCardController {
    @Autowired
    private UserService userService;

    @Autowired
    private BillingService billingService;

    @RequestMapping(value = "/addCreditCard", method = RequestMethod.POST)
    public ResponseEntity addCreditCard(@RequestBody Map<String, String> mp, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            /*Insert user_payment(card_name, card_number, cvc, default_payment, expiry_month,
             * exipy_year, holder_name, type, user_id);
             * */
            UserPayment userPayment = new UserPayment();

            userPayment.setUserId(userId);
            String cardName = mp.get("cardname");
            if(billingService.checkCardNameAndUserIdExist(cardName, userId)) {
                return new ResponseEntity("This cardname is exist", HttpStatus.BAD_REQUEST);
            }
            userPayment.setCardName(mp.get("cardname"));
            if(userPayment.getCardName() == null || userPayment.getCardName() == "") {
                return new ResponseEntity("your cardname is null", HttpStatus.BAD_REQUEST);
            }

            if(billingService.checkCardNumberAndUserIdExist(mp.get("cardnumber"), userId)) {
                return new ResponseEntity("This cardnumber is exist", HttpStatus.BAD_REQUEST);
            }
            userPayment.setCardNumber(mp.get("cardnumber"));
            if(userPayment.getCardNumber() == null || userPayment.getCardNumber() == "") {
                return new ResponseEntity("your cardnumber is null", HttpStatus.BAD_REQUEST);
            }

            String cvc = mp.get("cvc");
            userPayment.setCvc(Integer.parseInt(cvc));
            if(userPayment.getCvc() == null || userPayment.getCvc() == 0) {
                return new ResponseEntity("your cvc is null", HttpStatus.BAD_REQUEST);
            }

            String year = mp.get("year");
            userPayment.setExpiryYear(Integer.parseInt(year));
            if(userPayment.getExpiryYear() == null || userPayment.getExpiryYear() == 0) {
                return new ResponseEntity("your year is null", HttpStatus.BAD_REQUEST);
            }

            String month = mp.get("month");
            userPayment.setExpiryMonth(Integer.parseInt(month));
            if(userPayment.getExpiryMonth() == null || userPayment.getExpiryMonth() == 0) {
                return new ResponseEntity("your month is null", HttpStatus.BAD_REQUEST);
            }

            userPayment.setHolderName(mp.get("holdername"));
            if(userPayment.getHolderName() == "" || userPayment.getHolderName() == null) {
                return new ResponseEntity("your holdername is null", HttpStatus.BAD_REQUEST);
            }

            userPayment.setType(mp.get("type"));
            if(userPayment.getType() == "" || userPayment.getType() == null) {
                return new ResponseEntity("your type is null", HttpStatus.BAD_REQUEST);
            }

            if(!billingService.checkCardExist(userId)) {
                userPayment.setDefaultPayment(true);
            }
            else{
                userPayment.setDefaultPayment(false);
            }


            /*intsert into user_billing(user_billing_city, user_billing_country, user_billing_name)
             * (user_billing_state, user_billing_street1, user_billing_street2, use_billing_zipcode)
             * (user_payment_id)
             * */

            UserBilling userBilling = new UserBilling();
            userBilling.setUserBillingCity(mp.get("userbillingcity"));
            if(userBilling.getUserBillingCity() == null || userBilling.getUserBillingCity() == "") {
                return new ResponseEntity("your userbillingcity is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserBillingName(mp.get("userbillingname"));
            if(userBilling.getUserBillingName() == null || userBilling.getUserBillingName() == "") {
                return new ResponseEntity("your userbillingname is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserBillingState(mp.get("userbillingstate"));
            if(userBilling.getUserBillingState() == null || userBilling.getUserBillingState() == "") {
                return new ResponseEntity("your userbillingstate is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserBillingStreet1(mp.get("userbillingstreet1"));
            if(userBilling.getUserBillingStreet1() == null || userBilling.getUserBillingStreet1() == "") {
                return new ResponseEntity("your userbillingstreet1 is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserBillingStreet2(mp.get("userbillingstreet2"));
            if(userBilling.getUserBillingStreet2() == null || userBilling.getUserBillingStreet2() == "") {
                return new ResponseEntity("your userbillingstreet2 is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserBillingZipcode(mp.get("zipcode"));
            if(userBilling.getUserBillingZipcode() == null || userBilling.getUserBillingZipcode() == "") {
                return new ResponseEntity("your zipcode is null", HttpStatus.BAD_REQUEST);
            }

            /*一起插入防止只插入userPayment而没有插入userBilling*/

            billingService.insertUserPayment(userPayment);
            Long userPaymentId = billingService.getUserPaymentIdByUserIdAndCardName(userId, cardName);
            if(userPaymentId == null) {
                return new ResponseEntity("userPaymentId is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserPaymentId(userPaymentId);
            billingService.insertUserBilling(userBilling);
            return new ResponseEntity("Save successfully!", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/CreditCard", method = RequestMethod.GET)
    public ResponseEntity getCreditCard(String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            //通过userId得到一些user_payment
            List<Long> userPaymentIds = billingService.getUserPaymentIdsByUserId(userId);
            List<Map<String, String>> mps =  new ArrayList<Map<String, String>>();
            for (Long userPaymentId : userPaymentIds) {
                Map<String, String> mp1 = new HashMap<String, String>();
                UserPayment userPayment = billingService.getUserPaymentByUserPaymentId(userPaymentId);
                Long userBillingid = billingService.getUserBillingIdByUserPaymentId(userPaymentId);
                UserBilling userBilling = billingService.getUserBillingByUserBillingId(userBillingid);
                mp1.put("id", userPayment.getId().toString());
                mp1.put("default", userPayment.getDefaultPayment().toString());
                mp1.put("cardname", userPayment.getCardName().toString());
                mp1.put("cardnumber", userPayment.getCardNumber());
                mp1.put("cvc", userPayment.getCvc().toString());
                mp1.put("year", userPayment.getExpiryYear().toString());
                mp1.put("month", userPayment.getExpiryMonth().toString());
                mp1.put("holdername", userPayment.getHolderName());
                mp1.put("type", userPayment.getType());

                mp1.put("userbillingcity", userBilling.getUserBillingCity());
                mp1.put("userbillingname", userBilling.getUserBillingName());
                mp1.put("userbillingstate", userBilling.getUserBillingState());
                mp1.put("userbillingstreet1", userBilling.getUserBillingStreet1());
                mp1.put("userbillingstreet2", userBilling.getUserBillingStreet2());
                mp1.put("zipcode", userBilling.getUserBillingZipcode());
                mps.add(mp1);
            }
            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(mps, HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }
    @RequestMapping(value = "/CreditCard/{id}", method = RequestMethod.GET)
    public ResponseEntity get_UpdateCreditCard(@PathVariable("id") Long userPaymentId, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            Map<String, String> mp = new HashMap<>();
            UserPayment userPayment = billingService.getUserPaymentByUserPaymentId(userPaymentId);
            Long userBillingId = billingService.getUserBillingIdByUserPaymentId(userPaymentId);
            UserBilling userBilling = billingService.getUserBillingByUserBillingId(userBillingId);
            mp.put("userpaymentid", userPayment.getId().toString());
            mp.put("cardname", userPayment.getCardName());
            mp.put("cardnumber", userPayment.getCardNumber());
            mp.put("cvc", userPayment.getCvc().toString());
            mp.put("defaultpayment", userPayment.getDefaultPayment().toString());
            mp.put("month", userPayment.getExpiryMonth().toString());
            mp.put("year", userPayment.getExpiryYear().toString());
            mp.put("holdername", userPayment.getHolderName());
            mp.put("type", userPayment.getType());
            mp.put("userid", userPayment.getUserId().toString());

            mp.put("userbillingcity", userBilling.getUserBillingCity());
            mp.put("userbillingname", userBilling.getUserBillingName());
            mp.put("userbillingstate", userBilling.getUserBillingState());
            mp.put("userbillingstreet1", userBilling.getUserBillingStreet1());
            mp.put("userbillingstreet2", userBilling.getUserBillingStreet2());
            mp.put("zipcode", userBilling.getUserBillingZipcode());
            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(mp, HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/CreditCard/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateCreditCard(@PathVariable("id") Long userPaymentId, @RequestBody Map<String, String> mp, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            /*Update user_payment(card_name, card_number, cvc, default_payment, expiry_month,
             * exipy_year, holder_name, type, user_id);
             * */
            UserPayment userPayment = new UserPayment();
            userPayment.setId(userPaymentId);
            userPayment.setUserId(userId);
            String cardName = mp.get("cardname");
            userPayment.setCardName(mp.get("cardname"));
            if(billingService.checkCardNameAndUserIdExist(cardName, userId)) {
                if(billingService.getUserPaymentIdByUserIdAndCardName(userId, cardName) != userPaymentId) {
                    return new ResponseEntity("This cardname is exist", HttpStatus.BAD_REQUEST);
                }
            }
            if(userPayment.getCardName() == null || userPayment.getCardName() == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your cardname is null", HttpStatus.BAD_REQUEST);
            }

            userPayment.setCardNumber(mp.get("cardnumber"));
            if(userPayment.getCardNumber() == null || userPayment.getCardNumber() == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your cardnumber is null", HttpStatus.BAD_REQUEST);
            }
            String  cardNumber = mp.get("cardnumber");
            if(billingService.checkCardNumberAndUserIdExist(mp.get("cardnumber"), userId)) {
                if(billingService.getUserPaymentIdByUserIdAndCardNumber(userId, cardNumber) != userPaymentId) {
                    return new ResponseEntity("This cardnumber is exist", HttpStatus.BAD_REQUEST);
                }
            }

            String cvc = mp.get("cvc");
            userPayment.setCvc(Integer.parseInt(cvc));
            if(userPayment.getCvc() == null || userPayment.getCvc() == 0) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your cvc is null", HttpStatus.BAD_REQUEST);
            }

            String year = mp.get("year");
            userPayment.setExpiryYear(Integer.parseInt(year));
            if(userPayment.getExpiryYear() == null || userPayment.getExpiryYear() == 0) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your year is null", HttpStatus.BAD_REQUEST);
            }

            String month = mp.get("month");
            userPayment.setExpiryMonth(Integer.parseInt(month));
            if(userPayment.getExpiryMonth() == null || userPayment.getExpiryMonth() == 0) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your month is null", HttpStatus.BAD_REQUEST);
            }

            userPayment.setHolderName(mp.get("holdername"));
            if(userPayment.getHolderName() == "" || userPayment.getHolderName() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your holdername is null", HttpStatus.BAD_REQUEST);
            }

            userPayment.setType(mp.get("type"));
            if(userPayment.getType() == "" || userPayment.getType() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your type is null", HttpStatus.BAD_REQUEST);
            }

            userPayment.setDefaultPayment(billingService.getDefaultByUserPaymentId(userPaymentId));


            /*update into user_billing(user_billing_city, user_billing_country, user_billing_name)
             * (user_billing_state, user_billing_street1, user_billing_street2, use_billing_zipcode)
             * (user_payment_id)
             * */

            UserBilling userBilling = new UserBilling();
            userBilling.setUserBillingCity(mp.get("userbillingcity"));
            if(userBilling.getUserBillingCity() == null || userBilling.getUserBillingCity() == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your userbillingcity is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserBillingName(mp.get("userbillingname"));
            if(userBilling.getUserBillingName() == null || userBilling.getUserBillingName() == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your userbillingname is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserBillingState(mp.get("userbillingstate"));
            if(userBilling.getUserBillingState() == null || userBilling.getUserBillingState() == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your userbillingstate is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserBillingStreet1(mp.get("userbillingstreet1"));
            if(userBilling.getUserBillingStreet1() == null || userBilling.getUserBillingStreet1() == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your userbillingstreet1 is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserBillingStreet2(mp.get("userbillingstreet2"));
            if(userBilling.getUserBillingStreet2() == null || userBilling.getUserBillingStreet2() == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your userbillingstreet2 is null", HttpStatus.BAD_REQUEST);
            }

            userBilling.setUserBillingZipcode(mp.get("zipcode"));
            if(userBilling.getUserBillingZipcode() == null || userBilling.getUserBillingZipcode() == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("your zipcode is null", HttpStatus.BAD_REQUEST);
            }

            /*一起更新防止只更新userPayment而没有更新userBilling*/

            billingService.updateUserPayment(userPayment);
            //Long userPaymenId = billingService.getUserPaymentIdByCardNameAndUserId(cardName, userId);
            if(userPaymentId == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("userPaymentId is null", HttpStatus.BAD_REQUEST);
            }
            Long userBillingId = billingService.getUserBillingIdByUserPaymentId(userPaymentId);
            userBilling.setId(userBillingId);
            userBilling.setUserPaymentId(userPaymentId);
            billingService.updateUserBilling(userBilling);
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Save successfully!", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/updateDefault/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateDefault(@PathVariable("id") Long userPaymentId, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long oldUserPaymentid = billingService.getUserPaymentIdByDefaultTrue();
            UserPayment oldUserPayment = billingService.getUserPaymentByUserPaymentId(oldUserPaymentid);
            oldUserPayment.setDefaultPayment(false);
            UserPayment newUserPayment = billingService.getUserPaymentByUserPaymentId(userPaymentId);
            newUserPayment.setDefaultPayment(true);

            billingService.updateUserPayment(oldUserPayment);
            billingService.updateUserPayment(newUserPayment);
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Update Default Successfully", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/CreditCard/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletDefault(@PathVariable("id") Long userPaymentId, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            UserPayment userPayment = billingService.getUserPaymentByUserPaymentId(userPaymentId);
            Long userBillingId = billingService.getUserBillingIdByUserPaymentId(userPaymentId);
            UserBilling userBilling = billingService.getUserBillingByUserBillingId(userBillingId);
            billingService.delUserBilling(userBilling);
            billingService.delUserPayment(userPayment);

            Long oldUserPaymentId = billingService.getUserPaymentIdByDefaultTrue();
            if(oldUserPaymentId == 0l) {
                Long userId = ConstantUtils.userLoginMap.get(cookie);
                User user = userService.getUserById(userId);
                List<Long> userPaymentIds = billingService.getUserPaymentIdsByUserId(userId);
                if(userPaymentIds.size() == 0) {
                    httpServletResponse.setContentType("text/plain");
                    return new ResponseEntity("Your Billing is empty!", HttpStatus.OK);
                }
                Long tmpId = Long.MAX_VALUE;
                UserPayment tmpUserPayment = new UserPayment();
                for (Long paymentId : userPaymentIds) {
                    if(paymentId < tmpId) {
                        tmpUserPayment = billingService.getUserPaymentByUserPaymentId(paymentId);
                        tmpUserPayment.setDefaultPayment(true);
                        tmpId = paymentId;
                    }
                }
                billingService.updateUserPayment(tmpUserPayment);
            }
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Delete successfully", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }
}
