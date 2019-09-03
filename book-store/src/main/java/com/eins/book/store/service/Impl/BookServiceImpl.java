package com.eins.book.store.service.Impl;

import com.eins.book.store.dao.BookMapper;
import com.eins.book.store.dao.BookToCartItemMapper;
import com.eins.book.store.dao.CartItemMapper;
import com.eins.book.store.entity.Book;
import com.eins.book.store.entity.BookToCartItem;
import com.eins.book.store.entity.CartItem;
import com.eins.book.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private BookToCartItemMapper bookToCartItemMapper;

    @Override
    public Object getBookList(int l, int r) {
        List<Book> books = bookMapper.selectAll();
        int size = books.size();
        if(r > size) {
            return books.subList(l - 1, size);
        }
        return books.subList(l - 1, r);
    }

    @Override
    public Object getBookByID(Long id) {
        Example example = new Example(Book.class);
        example.createCriteria().andEqualTo("id", id);
        Book book = bookMapper.selectOneByExample(example);
        if(book != null){
            return book;
        }
        return null;
    }

    @Override
    public void InsertBook(Book book) {
        bookMapper.insert(book);
    }

    @Override
    public void UpdateBook(Book book) {
        bookMapper.updateByPrimaryKeySelective(book);
    }

    @Override
    public void DeleteBook(Book book) {
        bookMapper.delete(book);
    }

    @Override
    public Object getBookListByCategory(int l, int r, String category) {
        Example example = new Example(Book.class);
        example.createCriteria().andEqualTo("category", category);
        List<Book> books = bookMapper.selectByExample(example);
        int size = books.size();
        if(r > size) {
            return books.subList(l - 1, size);
        }
        return books.subList(l - 1, r);
    }

    @Override
    public double getUnitPriceByBookId(Long bookId) {
        Example example = new Example(Book.class);
        example.createCriteria().andEqualTo("id", bookId);
        Book book = bookMapper.selectOneByExample(example);
        return book.getOurPrice();
    }


}
