package com.eins.book.store.controller;

import com.eins.book.store.commons.ConstantUtils;
import com.eins.book.store.commons.FileUtils;
import com.eins.book.store.entity.Book;
import com.eins.book.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/booklist", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getBookList(int l, int r, String category, HttpServletResponse httpServletResponse) {

        if(l > r || l < 1) {
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Index error!", HttpStatus.BAD_REQUEST);
        }
        List<Book> tmpBookList;
        List<Book> bookList = new ArrayList<Book>();

        if(category == null) {
            tmpBookList = (List<Book>) bookService.getBookList(l, r);
        }
        else {
            tmpBookList = (List<Book>) bookService.getBookListByCategory(l, r, category);
        }
        for (Book book : tmpBookList) {
            if(book.getActive() == true) {
                bookList.add(book);
            }
        }
        httpServletResponse.setContentType("application/json");
        return new ResponseEntity(bookList, HttpStatus.OK);
    }

    @RequestMapping(value = "/adminbooklist", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAdminBookList(int l, int r, HttpServletResponse httpServletResponse) {

        if(l > r || l < 1) {
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Index error!", HttpStatus.BAD_REQUEST);
        }
        List<Book> bookList;
        bookList = (List<Book>) bookService.getBookList(l, r);
        httpServletResponse.setContentType("application/json");
        return new ResponseEntity(bookList, HttpStatus.OK);
    }
}
