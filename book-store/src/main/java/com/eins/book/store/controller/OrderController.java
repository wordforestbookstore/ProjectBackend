package com.eins.book.store.controller;

import com.eins.book.store.commons.ConstantUtils;
import com.eins.book.store.commons.CookieUtils;
import com.eins.book.store.commons.DateUtils;
import com.eins.book.store.commons.EmailUtils;
import com.eins.book.store.entity.*;
import com.eins.book.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Autowired
    private BillingService billingService;

    @Autowired
    private ShippingService shippingService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    public boolean checkBillingAddressNotNull(BillingAddress billingAddress) {

        return true;
    }

    @RequestMapping(value = "/Order", method = RequestMethod.POST)
    public ResponseEntity addOrder(@RequestBody Map<String, String> mp, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            //System.out.println(mp);
            String tmp = mp.get("ids");
            String[] tmps = tmp.split(",");
            List<Long> bookIds = new ArrayList<>();
            for (String s : tmps) {
                int len = s.length();
                Long x = 0l;
                x = (long)Integer.parseInt(s);
                bookIds.add(x);
            }
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            Long shoppingCartId = cartService.getShoppingCartIdByUserId(userId);
            Long OrderId = orderService.getUserOrderLastId() + 1;
            /*检查库存量是否足够*/
            for (Long bookId : bookIds) {
                Long cartItemId = cartService.getCartItemIdByBookIdAndCartId(bookId, shoppingCartId);
                CartItem cartItem = cartService.getCartItemByCartItemId(cartItemId);
                //cartItemList.add(cartItem);
                //cartItem.setOrderId(OrderId);
                //cartService.updateCartItem(cartItem);
                Book book = (Book) bookService.getBookByID(bookId);
                int denum = cartItem.getQty();
                int rest = book.getInStockNumber();
                if(denum > rest) {
                    httpServletResponse.setContentType("text/plain");
                    return new ResponseEntity("Instocknumber is not enough", HttpStatus.BAD_REQUEST);
                }
            }

            /*insert billing_address(city, country, name, state, street1, street2, zipcode, order_id)*/
            BillingAddress billingAddress = new BillingAddress();
            billingAddress.setBillingAddressCity(mp.get("userbillingcity"));
            billingAddress.setBillingAddressName(mp.get("userbillingname"));
            billingAddress.setBillingAddressState(mp.get("userbillingstate"));
            billingAddress.setBillingAddressStreet1(mp.get("userbillingstreet1"));
            billingAddress.setBillingAddressStreet2(mp.get("userbillingstreet2"));
            billingAddress.setBillingAddressZipcode(mp.get("userbillingzipcode"));
            billingAddress.setOrderId(OrderId);
            String billingAddressCity = billingAddress.getBillingAddressCity();
            if(billingAddressCity == null || billingAddressCity == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("billingAddressCity is null", HttpStatus.BAD_REQUEST);
            }
            String billingAddressName = billingAddress.getBillingAddressName();
            if(billingAddressName == null || billingAddressName == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("billingAddressName is null", HttpStatus.BAD_REQUEST);
            }
            String billingAddressState = billingAddress.getBillingAddressState();
            if(billingAddressState == null || billingAddressState == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("billingAddressState is null", HttpStatus.BAD_REQUEST);
            }
            String billingAddressStreet1 = billingAddress.getBillingAddressStreet1();
            if(billingAddressStreet1 == null || billingAddressStreet1 == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("billingAddressStreet1 is null", HttpStatus.BAD_REQUEST);
            }
            String billingAddressStreet2 = billingAddress.getBillingAddressStreet2();
            if(billingAddressStreet2 == null || billingAddressStreet2 == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("billingAddressStreet2 is null", HttpStatus.BAD_REQUEST);
            }
            String billingAddressZipCode = billingAddress.getBillingAddressZipcode();
            if(billingAddressZipCode == null || billingAddressZipCode == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("billingAddressZipCode is null", HttpStatus.BAD_REQUEST);
            }
            Long OrderId1 = billingAddress.getOrderId();
            if(OrderId1 == null ||OrderId1 == 0) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("OrderId is null", HttpStatus.BAD_REQUEST);
            }
            /*insert payment(cardName, cardNumber, cvc, month, year, holderName, oder_id, type)*/
            Payment payment = new Payment();
            payment.setHolderName(mp.get("holdername"));
            payment.setCvc(Integer.parseInt(mp.get("cvc")));
            payment.setExpiryMonth(Integer.parseInt(mp.get("month")));
            payment.setExpiryYear(Integer.parseInt(mp.get("year")));
            payment.setCardNumber(mp.get("cardnumber"));
            payment.setOrderId(OrderId);
            payment.setType(mp.get("type"));
            String paymentHolderName = payment.getHolderName();
            if(paymentHolderName == null || paymentHolderName == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("paymentHolderName is null", HttpStatus.BAD_REQUEST);
            }
            String cardNumber = payment.getCardNumber();
            if(cardNumber == null || cardNumber == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("paymentNumber is null", HttpStatus.BAD_REQUEST);
            }
            String type = payment.getType();
            if(type == null || type == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("paymentHolderName is null", HttpStatus.BAD_REQUEST);
            }
            String Month = payment.getExpiryMonth().toString();
            if(Month == "" || Month == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Month is null", HttpStatus.BAD_REQUEST);
            }
            String Year = payment.getExpiryYear().toString();
            if(Year == "" || Year == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Year is null", HttpStatus.BAD_REQUEST);
            }

            /*insert  shippingAddress(city, country, name, state, street1, street2, zipcode, oderId*/
            ShippingAddress shippingAddress = new ShippingAddress();
            shippingAddress.setOrderId(OrderId);
            shippingAddress.setShippingAddressCity(mp.get("shippingcity"));
            shippingAddress.setShippingAddressName(mp.get("shippingname"));
            shippingAddress.setShippingAddressState(mp.get("shippingstate"));
            shippingAddress.setShippingAddressStreet1(mp.get("shippingstreet1"));
            shippingAddress.setShippingAddressStreet2(mp.get("shippingstreet2"));
            shippingAddress.setShippingAddressZipcode(mp.get("shippingzipcode"));
            String shippingCity = shippingAddress.getShippingAddressCity();
            if(shippingCity == null ||shippingCity == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("shippingcity is null", HttpStatus.BAD_REQUEST);
            }
            String shippingName = shippingAddress.getShippingAddressName();
            if(shippingName == null || shippingName == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("shippingname is null", HttpStatus.BAD_REQUEST);
            }
            String shippingState = shippingAddress.getShippingAddressState();
            if(shippingState == null || shippingState == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("shippingState is null", HttpStatus.BAD_REQUEST);
            }
            String shippingStreet1 = shippingAddress.getShippingAddressStreet1();
            if(shippingStreet1 == null || shippingStreet1 == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("shippingStreet1 is null", HttpStatus.BAD_REQUEST);
            }
            String shippingStreet2 = shippingAddress.getShippingAddressStreet2();
            if(shippingStreet2 == null || shippingStreet2 == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("shippingStreet2 is null", HttpStatus.BAD_REQUEST);
            }
            String shippingZipcode = shippingAddress.getShippingAddressZipcode();
            if(shippingZipcode == null || shippingZipcode == "") {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("shippingZipcode is null", HttpStatus.BAD_REQUEST);
            }

            /*insert user_oder(billing_address_id, order_date, order_status, order_total, payment_id)
            * shipping_address_id, shipping_date, shipping_method, user_id*/
            UserOrder userOrder = new UserOrder();
            userOrder.setUserId(userId);
            if(userOrder.getUserId() == 0) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("userId is null", HttpStatus.BAD_REQUEST);
            }
            userOrder.setShippingMethod(mp.get("shippingMethod"));
            if(userOrder.getShippingMethod() == "" || userOrder.getShippingMethod() == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("shippingMethod is null", HttpStatus.BAD_REQUEST);
            }
            Date now = DateUtils.getNowDate();
            userOrder.setOrderDate(now);
            userOrder.setShippingDate(now);
            if(userOrder.getOrderDate() == null || userOrder.getShippingDate() == null ) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Date is null", HttpStatus.BAD_REQUEST);
            }
            userOrder.setOrderStatus("created");
            BigDecimal sum = new BigDecimal(mp.get("ordertotal"));
            userOrder.setOrderTotal(sum);
            /*开始插入*/
            billingAddress.setOrderId(null);
            payment.setOrderId(null);
            shippingAddress.setOrderId(null);
            billingService.insertBillingAddress(billingAddress);
            paymentService.insertPayment(payment);
            shippingService.insertShippingAddress(shippingAddress);
            Long billingAddressId = billingService.getBillingAddressIdByOrderIdNull();//billingService.getBillingAddressIdByOrderId(null);
            Long paymentId = paymentService.getPaymentIdByOrderIdNull();//paymentService.getPaymentIdByOrderId(null);
            Long shippingAddressId = shippingService.getShippingAddressIdByOrderIdNull();//shippingService.getShippingAddressIdByOrderId(null);
            userOrder.setId(OrderId);
            userOrder.setBillingAddressId(billingAddressId);
            userOrder.setPaymentId(paymentId);
            userOrder.setShippingAddressId(shippingAddressId);
            orderService.insertUserOrder(userOrder);
            //System.out.println("OrderId: " + OrderId);
            billingAddress.setOrderId(OrderId);
            payment.setOrderId(OrderId);
            shippingAddress.setOrderId(OrderId);
            billingService.updateBillingAddress(billingAddress);
            paymentService.updatePayment(payment);
            shippingService.updateShippingAddress(shippingAddress);
            List<CartItem> cartItemList = new ArrayList<CartItem>();
            /*update cartItem, book, shoppingCart*/
            for (Long bookId : bookIds) {
                Long cartItemId = cartService.getCartItemIdByBookIdAndCartId(bookId, shoppingCartId);
                CartItem cartItem = cartService.getCartItemByCartItemId(cartItemId);
                cartItemList.add(cartItem);
                cartItem.setOrderId(OrderId);
                cartService.updateCartItem(cartItem);
                Book book = (Book) bookService.getBookByID(bookId);
                int denum = cartItem.getQty();
                int rest = book.getInStockNumber() - denum;
                book.setInStockNumber(rest);
                bookService.updateBook(book);
            }
            ShoppingCart shoppingCart = cartService.getShoppingCartByShoppingCartId(shoppingCartId);
            BigDecimal oldmoney = shoppingCart.getGrandTotal();
            BigDecimal tmp2 = new BigDecimal(mp.get("subtotal"));
            oldmoney = oldmoney.subtract(tmp2);
            shoppingCart.setGrandTotal(oldmoney);
            cartService.updateShoppingCart(shoppingCart);
            /*邮件*/

            new EmailUtils().sendcart(user.getEmail(), OrderId, user.getUsername(), billingAddressName, billingAddressStreet1, billingAddressStreet2, billingAddressCity, billingAddressState, billingAddressZipCode, paymentHolderName, type, cardNumber, Month, Year, shippingName, shippingStreet1, shippingStreet2, shippingCity, shippingState, shippingZipcode, new EmailUtils().initSummary(cartItemList));
            return new ResponseEntity("Successfully", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/Order", method = RequestMethod.GET)
    public ResponseEntity getOrder(String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userId = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userId);
            Long shoppingCartId = cartService.getShoppingCartIdByUserId(userId);
            List<Map<String, String>> mps = new ArrayList<Map<String, String>>();
            List<Long> userOrderIds = orderService.getUserOrderIdsByUserId(userId);
            int num = 0;
            for (Long userOrderId : userOrderIds) {
                num++;
                UserOrder userOrder = orderService.getUserOrderByUserOrderId(userOrderId);
                Map<String, String> mp = new HashMap<>();
                mp.put("orderdate", DateUtils.getStringDate(userOrder.getOrderDate()));
                mp.put("orderstatus", userOrder.getOrderStatus());
                mp.put("ordernumber", String.valueOf(num));
                mp.put("ordertotal", userOrder.getOrderTotal().toString());
                mp.put("orderid", userOrder.getId().toString());


                Long paymentId = userOrder.getPaymentId();
                Long billingAddressId = userOrder.getBillingAddressId();
                Long shippingAddressId = userOrder.getShippingAddressId();
                Payment payment = paymentService.getPaymentById(paymentId);
                BillingAddress billingAddress = billingService.getBillingAdressById(billingAddressId);
                ShippingAddress shippingAddress = shippingService.getShippingAddressById(shippingAddressId);
                mp.put("billingaddressname", billingAddress.getBillingAddressName());
                mp.put("billingaddressstreet1", billingAddress.getBillingAddressStreet1());
                mp.put("billingaddressstreet2", billingAddress.getBillingAddressStreet2());
                mp.put("billingaddresscity", billingAddress.getBillingAddressCity());
                mp.put("billingaddressstate", billingAddress.getBillingAddressState());
                mp.put("billintaddresszipcode", billingAddress.getBillingAddressZipcode());
                mp.put("holdername", payment.getHolderName());
                mp.put("type", payment.getType());
                mp.put("cardnumber", payment.getCardNumber());
                mp.put("month", payment.getExpiryMonth().toString());
                mp.put("year", payment.getExpiryYear().toString());
                mp.put("shippingaddressname", shippingAddress.getShippingAddressName());
                mp.put("shippingaddressstreet1", shippingAddress.getShippingAddressStreet1());
                mp.put("shippingaddressstreet2", shippingAddress.getShippingAddressStreet2());
                mp.put("shippingaddresscity", shippingAddress.getShippingAddressCity());
                mp.put("shippingaddressstate", shippingAddress.getShippingAddressState());
                mp.put("shippingaddresszipcode", shippingAddress.getShippingAddressZipcode());
                mps.add(mp);
            }
            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(mps, HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/Order/{id}", method = RequestMethod.GET)
    public ResponseEntity getOrderSp(@PathVariable("id") Long userOrderId, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            List<Long> cartItemIds = cartService.getCartItemIdsByUserOrderId(userOrderId);
            List<Map<String, String>> mps = new ArrayList<Map<String, String>>();
            for (Long cartItemId : cartItemIds) {
                Map<String, String> mp = new HashMap<String, String>();
                CartItem cartItem = cartService.getCartItemByCartItemId(cartItemId);
                Long bookId = cartItem.getBookId();
                Book book = (Book) bookService.getBookByID(bookId);
                mp.put("itemname", book.getTitle());
                mp.put("itemprice", book.getOurPrice().toString());
                mp.put("itemquantity", cartItem.getQty().toString());
                mp.put("total", cartItem.getSubtotal().toString());
                mp.put("weight", book.getShippingWeight().toString());
                mps.add(mp);
            }
            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(mps, HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }
}
