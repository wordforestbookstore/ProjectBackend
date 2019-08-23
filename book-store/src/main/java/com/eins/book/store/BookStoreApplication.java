package com.eins.book.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 应用程序入口
 */
@RestController
@MapperScan(basePackages = "com.eins.book.store.dao")
@SpringBootApplication
public class BookStoreApplication extends SpringBootServletInitializer {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String main() {
        return "首页";
    }

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    //打包项目
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
