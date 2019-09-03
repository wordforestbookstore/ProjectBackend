package com.eins.book.store.controller;

import com.eins.book.store.commons.CookieUtils;
import com.eins.book.store.commons.FileUtils;
import com.eins.book.store.entity.Book;
import com.eins.book.store.service.BookService;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getBook(@PathVariable("id") Long id, String cookie, HttpServletResponse httpServletResponse) {
        /*if (CookieUtils.CookieConfirm(cookie)) {*/
            Book book = (Book) bookService.getBookByID(id);
            if (book != null) {
                httpServletResponse.setContentType("application/json");
                return new ResponseEntity(book, HttpStatus.OK);
            }
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Book not exits!", HttpStatus.BAD_REQUEST);
        /*}*/

        //httpServletResponse.setContentType("text/plain");
       // return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/img/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity putBookImage(@PathVariable("id") String id, MultipartFile file, HttpServletResponse httpServletResponse) {
        FileUtils.upload(id, file, "/usr/bookstore/webapp/upload");

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity postBook(@RequestBody Book ReqBook, String cookie, HttpServletResponse httpServletResponse) {
        if(CookieUtils.CookieConfirm(cookie)) {
            bookService.InsertBook(ReqBook);
//            FileUtils.upload(file,"/static");

            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(ReqBook, HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateBook(@PathVariable("id") Long id, @RequestBody Book newBook, String cookie, HttpServletResponse httpServletResponse) {
        if(CookieUtils.CookieConfirm(cookie)) {
            Book book = (Book) bookService.getBookByID(id);
            if(book != null) {
                bookService.UpdateBook(newBook);
                httpServletResponse.setContentType("application/json");
                return  new ResponseEntity(newBook, HttpStatus.OK);
            }
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Book not exits!", HttpStatus.BAD_REQUEST);
        }

        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteBook(@PathVariable("id") Long id, String cookie, HttpServletResponse httpServletResponse) {
        if(CookieUtils.CookieConfirm(cookie)) {
            Book book = (Book) bookService.getBookByID(id);
            bookService.DeleteBook(book);
            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(book, HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }
}
