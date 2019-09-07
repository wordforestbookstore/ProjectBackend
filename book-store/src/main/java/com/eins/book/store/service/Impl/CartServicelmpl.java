package com.eins.book.store.service.Impl;

import com.eins.book.store.dao.BookMapper;
import com.eins.book.store.dao.BookToCartItemMapper;
import com.eins.book.store.dao.CartItemMapper;
import com.eins.book.store.dao.ShoppingCartMapper;
import com.eins.book.store.entity.BookToCartItem;
import com.eins.book.store.entity.CartItem;
import com.eins.book.store.entity.ShoppingCart;
import com.eins.book.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServicelmpl implements CartService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private BookToCartItemMapper bookToCartItemMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    /*插入表cart_item*/
    @Override
    public void insertCartItem(CartItem cartItem) {
        cartItemMapper.insertSelective(cartItem);
    }

    /*通过bookid和shoppingCartId获得CartItemId*/
    @Override
    public Long getCartItemIdByBookIdAndCartId(Long bookId, Long shoppingCartId) {
        Example example = new Example(CartItem.class);
        example.createCriteria().andEqualTo("bookId", bookId).andEqualTo("shoppingCartId", shoppingCartId);
        List<CartItem> cartItems = cartItemMapper.selectByExample(example);
        Long ans = 0l;
        for (CartItem cartItem : cartItems) {
            if(cartItem.getOrderId() == null) {
                ans = cartItem.getId();
                break;
            }
        }
        return ans;
    }

    /*通过shoppingCartId获得购物车内所有的cartItemId*/
    @Override
    public List<Long> getCarItemIdsByShoppingCartId(Long shoppingCartId) {
        Example example = new Example(CartItem.class);
        example.createCriteria().andEqualTo("shoppingCartId", shoppingCartId);
        List<CartItem> cartItems = cartItemMapper.selectByExample(example);
        List<Long> cartItemIds = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            if(cartItem.getOrderId() == null) cartItemIds.add(cartItem.getId());
        }
        return cartItemIds;
    }
    /*通过userorderId获得CartItemIds*/
    @Override
    public List<Long> getCartItemIdsByUserOrderId(Long userOrderId) {
        Example example = new Example(CartItem.class);
        example.createCriteria().andEqualTo("orderId", userOrderId);
        List<CartItem> cartItems = cartItemMapper.selectByExample(example);
        List<Long> cartItemIds = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            cartItemIds.add(cartItem.getId());
        }
        return cartItemIds;
    }

    /*检查bookid是否在Cartitem中*/
    @Override
    public boolean checkBookIdExistInCartItem(Long bookId, Long shoppingCartId) {
        Example example = new Example(CartItem.class);
        example.createCriteria().andEqualTo("bookId", bookId).andEqualTo("shoppingCartId", shoppingCartId);
        List<CartItem> cartItems = cartItemMapper.selectByExample(example);
        boolean flag = false;
        for (CartItem cartItem : cartItems) {
            if(cartItem.getOrderId() == null) {
                flag = true;
            }
        }
        if(flag == true) return true;
        return false;
//        if(cartItem == null) {
//            return false;
//        }
//        else {
//            return true;
//        }
    }

    /*通过CartItemid获得CartItem*/
    @Override
    public CartItem getCartItemByCartItemId(Long CartItemId) {
        Example example = new Example(CartItem.class);
        example.createCriteria().andEqualTo("id", CartItemId);
        CartItem cartItem = cartItemMapper.selectOneByExample(example);
        return cartItem;
    }

    /*更新CartItem*/
    @Override
    public  void updateCartItem(CartItem cartItem) {
        cartItemMapper.updateByPrimaryKeySelective(cartItem);
    }

    /*删除CarItem*/
    @Override
    public void delCartItem(CartItem cartItem) {
        cartItemMapper.deleteByPrimaryKey(cartItem);
    }

    /*插入bookToCartItem*/
    @Override
    public void insertBookToCartItem(BookToCartItem bookToCartItem) {
        bookToCartItemMapper.insert(bookToCartItem);
    }

    /*通过CartItemId获得BookToCartItemId*/
    @Override
    public Long getBookToCartItemIdByCartItemId(Long cartItemId) {
        Example example = new Example(BookToCartItem.class);
        example.createCriteria().andEqualTo("cartItemId", cartItemId);
        BookToCartItem bookToCartItem = bookToCartItemMapper.selectOneByExample(example);

        return bookToCartItem.getId();
    }

    /*通过BookToCartItemId 获得BookToCartItem*/
    @Override
    public BookToCartItem getBookToCartItemByBookToCartItemId(Long bookToCartItemId) {
        Example example = new Example(BookToCartItem.class);
        example.createCriteria().andEqualTo("id", bookToCartItemId);
        BookToCartItem bookToCartItem = bookToCartItemMapper.selectOneByExample(example);
        return bookToCartItem;
    }

    /*删除bookToCartItem*/
    @Override
    public void delBookToCartItem(BookToCartItem bookToCartItem) {
        bookToCartItemMapper.deleteByPrimaryKey(bookToCartItem);
    }

    /*通过用户id获得购物车id*/
    @Override
    public Long getShoppingCartIdByUserId(Long userId) {
        Example example = new Example(ShoppingCart.class);
        example.createCriteria().andEqualTo("userId", userId);
        ShoppingCart shoppingCart = shoppingCartMapper.selectOneByExample(example);
        if(shoppingCart == null) {
            return 0l;
        }
        else {
            return shoppingCart.getId();
        }
    }

    /*通过购物车id获得购物车*/
    @Override
    public ShoppingCart getShoppingCartByShoppingCartId(Long shoppingCartId) {
        Example example = new Example(ShoppingCart.class);
        example.createCriteria().andEqualTo("id", shoppingCartId);
        ShoppingCart shoppingCart = shoppingCartMapper.selectOneByExample(example);
        if(shoppingCart == null) {
        }
        return shoppingCart;
    }


    /*插入表shoppingcart*/
    @Override
    public void insertShoppingCart(ShoppingCart shoppingCart) {
        shoppingCartMapper.insert(shoppingCart);
    }

    /*更新shoppingcart*/
    @Override
    public void updateShoppingCart(ShoppingCart shoppingCart) {
        shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
    }
}
