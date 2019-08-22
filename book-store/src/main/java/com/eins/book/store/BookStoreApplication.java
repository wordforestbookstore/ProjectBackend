package com.eins.book.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 应用程序入口
 */
@RestController
@MapperScan(basePackages = "com.eins.book.store.dao")
@SpringBootApplication
public class BookStoreApplication {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String main() {
        return "首页";
    }

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

}
