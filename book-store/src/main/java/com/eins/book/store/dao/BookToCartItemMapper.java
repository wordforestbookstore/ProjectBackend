package com.eins.book.store.dao;

import com.eins.book.store.entity.BookToCartItem;
import tk.mybatis.MyMapper;

/**
 * 加入购物车的图书 DAO
 */
public interface BookToCartItemMapper extends MyMapper<BookToCartItem> {
}