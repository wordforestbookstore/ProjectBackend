package com.eins.book.store.controller;

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
        List<Book> bookList = null;
        if(category == null) {
            bookList = (List<Book>) bookService.getBookList(l, r);
        }
        else {
            bookList = (List<Book>) bookService.getBookListByCategory(l, r, category);
        }
        httpServletResponse.setContentType("application/json");
        return new ResponseEntity(bookList, HttpStatus.OK);
    }
}
