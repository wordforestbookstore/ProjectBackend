package com.eins.book.store.service.Impl;

import com.eins.book.store.dao.BookMapper;
import com.eins.book.store.entity.Book;
import com.eins.book.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

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
    public Object getBookByID(int id) {
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
}
