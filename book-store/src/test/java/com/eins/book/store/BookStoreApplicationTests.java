package com.eins.book.store;

import com.eins.book.store.commons.EncryptUtil;
import com.eins.book.store.dao.BookMapper;
import com.eins.book.store.entity.Book;
import com.eins.book.store.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookStoreApplication.class)
@Transactional
@Rollback
public class BookStoreApplicationTests {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookService bookService;

    @Test
    public void testSelect() {
        List<Book> books = bookMapper.selectAll();
        for (Book book : books) {
            System.out.println(book.getTitle());
        }
    }

    @Test
    public void testget() {
        List<Book> bookList = (List<Book>) bookService.getBookList(1, 100);
        int i = 0;
        for (Book book : bookList) {
            System.out.print(++i + ":");
            System.out.println(book.getTitle());
        }
    }

    @Test
    public void testSha256() {
        String str = "123456";
        System.out.println(EncryptUtil.getSha256(str));
    }
}
