package com.eins.book.store.service;


import com.eins.book.store.entity.Book;
import com.eins.book.store.entity.BookToCartItem;
import com.eins.book.store.entity.CartItem;

import java.util.List;

public interface BookService {
    public Object getBookList(int l, int r);
    public Object getBookByID(Long id);
    public void InsertBook(Book book);
    public void UpdateBook(Book book);
    public void DeleteBook(Book book);
    public Object getBookListByCategory(int l, int r, String category);
    public double getUnitPriceByBookId(Long bookId);
    public int getInStockNumberByBookId(Long bookId);
    public void updateBook(Book book);


}
