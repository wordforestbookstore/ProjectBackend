package com.eins.book.store;

import com.eins.book.store.commons.DateUtils;
import com.eins.book.store.commons.EmailUtils;
import com.eins.book.store.commons.EncryptUtil;
import com.eins.book.store.dao.BookMapper;
import com.eins.book.store.dao.CartItemMapper;
import com.eins.book.store.entity.Book;
import com.eins.book.store.entity.CartItem;
import com.eins.book.store.service.BookService;
import com.eins.book.store.service.CartService;
import com.eins.book.store.service.Impl.UserServiceImpl;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CartItemMapper cartItemMapper;

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

    @Test
    public void testdate() {
        String OrderString = "";
        for(int i = 0;i < 10;i++) {
            OrderString += String.valueOf(i);
        }
        System.out.println(OrderString);
    }

    @Test
    public void testcartemail() {
        List<CartItem> cartItems = cartItemMapper.selectAll();
        new EmailUtils().sendcart("1243299228@qq.com", 1l, "MZX", "MZX", "XLW", "NJUST", "cn", "NJ", "210094", "MZX", "visa", "123", "4", "20", "MZX", "XLW", "NJUST", "cn", "NJ", "210094", new EmailUtils().initSummary(cartItems));
    }
    @Test
    public void testnumber() {
        String tmp = "13453,446,46313,015";
        String[] tmps = tmp.split(",");
        List<Long> bookids = new ArrayList<>();
        for (String s : tmps) {
            System.out.println(s);
            int len = s.length();
            Long x = 0l;
            x = (long)Integer.parseInt(s);
                /*for(int i = 0;i < len;i++) {
                    x = 10l * x + Integer.parseInt(s[i]);
                }*/
            bookids.add(x);
            System.out.println(x);
        }
    }
}
