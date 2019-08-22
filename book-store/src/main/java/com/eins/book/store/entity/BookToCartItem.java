package com.eins.book.store.entity;

import javax.persistence.*;

/**
 * 加入购物车的图书 实体
 */
@Table(name = "bookstoredatabase.book_to_cart_item")
public class BookToCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "cart_item_id")
    private Long cartItemId;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return book_id
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * @param bookId
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * @return cart_item_id
     */
    public Long getCartItemId() {
        return cartItemId;
    }

    /**
     * @param cartItemId
     */
    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }
}