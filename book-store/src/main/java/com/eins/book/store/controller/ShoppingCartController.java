package com.eins.book.store.controller;


import com.eins.book.store.commons.ConstantUtils;
import com.eins.book.store.commons.CookieUtils;
import com.eins.book.store.entity.*;
import com.eins.book.store.service.BookService;
import com.eins.book.store.service.CartService;
import com.eins.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShoppingCartController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    /*添加购物车*/
    @RequestMapping(value = "/shopping/{id}", method = RequestMethod.POST)
    public ResponseEntity addCart(@PathVariable("id") Long bookId, int num, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userid = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userid);
            Long shoppingId = cartService.getShoppingCartIdByUserId(userid);
            if(cartService.checkBookIdExistInCartItem(bookId, shoppingId)) {
                /*update cart_item (id, bookid, orderid, qty, shopping_cart_id, subtotal) */
                Long shoppingCartId = cartService.getShoppingCartIdByUserId(userid);
                BigDecimal totalPrice = new BigDecimal(bookService.getUnitPriceByBookId(bookId) * (double)num);
                BigDecimal addPrice = new BigDecimal(bookService.getUnitPriceByBookId(bookId) * (double)num);
                Long cartItemId = cartService.getCartItemIdByBookIdAndCartId(bookId, shoppingCartId);
                CartItem cartItem = cartService.getCartItemByCartItemId(cartItemId);
                num = cartItem.getQty() + num;
                cartItem.setQty(num);

                totalPrice = totalPrice.add(cartItem.getSubtotal());
                cartItem.setSubtotal(totalPrice);
                cartService.updateCartItem(cartItem);
                /**/

                /*update shopping_cart*/
                // Long shoppingCartId = userService.getShoppingCartIdByUserId(userid);
                ShoppingCart shoppingCart = cartService.getShoppingCartByShoppingCartId(shoppingCartId);
                BigDecimal totalMoney = shoppingCart.getGrandTotal().add(addPrice);
                shoppingCart.setGrandTotal(totalMoney);
                cartService.updateShoppingCart(shoppingCart);
            }
            else {
                Long shoppingCartId = cartService.getShoppingCartIdByUserId(userid);
                BigDecimal totalPrice = new BigDecimal(bookService.getUnitPriceByBookId(bookId) * (double) num);

                /*insert cart_item (id, bookid, orderid, qty, shopping_cart_id, subtotal) */
                CartItem cartItem = new CartItem();
                cartItem.setBookId(bookId);
                cartItem.setOrderId(null);
                cartItem.setQty(num);
                cartItem.setShoppingCartId(shoppingCartId);
                cartItem.setSubtotal(totalPrice);
                cartService.insertCartItem(cartItem);

                /*insert book_to_cart_item (book_id, cart_item_id)*/
                BookToCartItem bookToCartItem = new BookToCartItem();
                Long cartItemId = cartService.getCartItemIdByBookIdAndCartId(bookId, shoppingCartId);
                bookToCartItem.setBookId(bookId);
                bookToCartItem.setCartItemId(cartItemId);
                cartService.insertBookToCartItem(bookToCartItem);

                /*update shopping_cart*/
                ShoppingCart shoppingCart = cartService.getShoppingCartByShoppingCartId(shoppingCartId);
                BigDecimal totalMoney = shoppingCart.getGrandTotal().add(totalPrice);
                shoppingCart.setGrandTotal(totalMoney);
                cartService.updateShoppingCart(shoppingCart);
            }

            return new ResponseEntity("Add successfully", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    /*购物车中删除*/
    @RequestMapping(value = "/cart/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCart(@PathVariable("id") Long bookId, int num, String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userid = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userid);
            Long shoppingCartId = cartService.getShoppingCartIdByUserId(userid);
            Long cartItemId = cartService.getCartItemIdByBookIdAndCartId(bookId, shoppingCartId);
            CartItem cartItem = cartService.getCartItemByCartItemId(cartItemId);
            int restnum = cartItem.getQty();
            if(num != restnum) {
                //update cart_item (id, bookid, orderid, qty, shopping_cart_id, subtotal)
                double dePrice = bookService.getUnitPriceByBookId(bookId) * num;
                double resPrice = cartItem.getSubtotal().doubleValue();
                BigDecimal tmp = new BigDecimal(resPrice - dePrice);
                cartItem.setSubtotal(tmp);
                cartItem.setQty(restnum - num);
                cartService.updateCartItem(cartItem);

                /*update shoppingCart(id, grand_total, user_id)*/
                ShoppingCart shoppingCart = cartService.getShoppingCartByShoppingCartId(shoppingCartId);
                BigDecimal tmpcost = new BigDecimal(dePrice);
                BigDecimal money = shoppingCart.getGrandTotal().subtract(tmpcost);
                shoppingCart.setGrandTotal(money);
                cartService.updateShoppingCart(shoppingCart);
            }
            else {
                /*delet book_to_cart_item*/
                Long bookToCartItemId = cartService.getBookToCartItemIdByCartItemId(cartItemId);
                BookToCartItem bookToCartItem = cartService.getBookToCartItemByBookToCartItemId(bookToCartItemId);
                cartService.delBookToCartItem(bookToCartItem);

                //delet cart_item (id, bookid, orderid, qty, shopping_cart_id, subtotal)
                double dePrice = cartItem.getSubtotal().doubleValue();
                cartService.delCartItem(cartItem);

                /*update shoppingCart(id, grand_total, user_id)*/
                ShoppingCart shoppingCart = cartService.getShoppingCartByShoppingCartId(shoppingCartId);
                double money = shoppingCart.getGrandTotal().doubleValue() - dePrice;
                BigDecimal tmpcost = new BigDecimal(money);
                shoppingCart.setGrandTotal(tmpcost);
                cartService.updateShoppingCart(shoppingCart);


            }
            return new ResponseEntity("Delet book successfully", HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    /*获取购物车*/
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getCart(String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if(CookieUtils.CookieConfirm(cookie)) {
            Long userid = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userid);
            Long shoppingCartId = cartService.getShoppingCartIdByUserId(userid);

            List<Long> cartItemIds = cartService.getCarItemIdsByShoppingCartId(shoppingCartId);
            List<Map<String, String>> books = new ArrayList<Map<String, String>>();

            for (Long cartItemId : cartItemIds) {
                //bookIds.add();
                CartItem cartItem = cartService.getCartItemByCartItemId(cartItemId);
                Long bookId = cartItem.getBookId();
                Book book = (Book) bookService.getBookByID(bookId);
                if(cartItem.getOrderId() != null) continue;
                Map<String, String> mp = new HashMap<String, String>();
                Long num = (long) cartItem.getQty();
                mp.put("number", num.toString());
                mp.put("title", book.getTitle());
                mp.put("author", book.getAuthor());
                mp.put("ourPrice", book.getOurPrice().toString());
                mp.put("category", book.getCategory());
                mp.put("id", book.getId().toString());
                mp.put("inStockNumber", book.getInStockNumber().toString());
                mp.put("weight", book.getShippingWeight().toString());
                books.add(mp);
            }
            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(books, HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }




}