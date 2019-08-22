package com.eins.book.store;

import com.eins.book.store.dao.BookMapper;
import com.eins.book.store.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookStoreApplication.class)
@Transactional
@Rollback
public class BookStoreApplicationTests {

    @Autowired
    private BookMapper bookMapper;

    @Test
    public void testSelect() {
        List<Book> books = bookMapper.selectAll();
        for (Book book : books) {
            System.out.println(book.getTitle());
        }
    }

}
