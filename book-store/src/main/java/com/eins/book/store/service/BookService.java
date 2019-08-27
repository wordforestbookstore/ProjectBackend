package com.eins.book.store.service;


import com.eins.book.store.entity.Book;

public interface BookService {
    public Object getBookList(int l, int r);
    public Object getBookByID(int id);
    public void InsertBook(Book book);
    public void UpdateBook(Book book);
    public void DeleteBook(Book book);
}
