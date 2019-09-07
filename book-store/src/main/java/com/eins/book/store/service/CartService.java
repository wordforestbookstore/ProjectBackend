package com.eins.book.store.service;

import com.eins.book.store.entity.BookToCartItem;
import com.eins.book.store.entity.CartItem;
import com.eins.book.store.entity.ShoppingCart;

import java.util.List;

public interface CartService {
    /*(book)Shoppingcart*/

    /*CartItem*/
    public void insertCartItem(CartItem bookToCartItem);
    public Long getCartItemIdByBookIdAndCartId(Long bookId, Long shoppingCartId);
    public List<Long> getCarItemIdsByShoppingCartId(Long shoppingCartId);
    public List<Long> getCartItemIdsByUserOrderId(Long UserOrderId);
    public boolean checkBookIdExistInCartItem(Long bookId, Long shoppingCartId);
    public CartItem getCartItemByCartItemId(Long CartItemId);
    public void updateCartItem(CartItem cartItem);
    public void delCartItem(CartItem cartItem);


    /*BookToCartItem*/
    public void insertBookToCartItem(BookToCartItem bookToCartItem);
    public Long getBookToCartItemIdByCartItemId(Long cartItemId);
    public BookToCartItem getBookToCartItemByBookToCartItemId(Long bookToCartItemId);
    public  void delBookToCartItem(BookToCartItem bookToCartItem);

    /*ShoppingCart*/
    public Long getShoppingCartIdByUserId(Long userId);
    public ShoppingCart getShoppingCartByShoppingCartId(Long shoppingCartId);
    public void insertShoppingCart(ShoppingCart shoppingCart);
    public void updateShoppingCart(ShoppingCart shoppingCart);
}
