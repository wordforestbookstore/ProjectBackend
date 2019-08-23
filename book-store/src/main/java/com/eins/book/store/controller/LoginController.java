package com.eins.book.store.controller;

import com.eins.book.store.commons.ConstantUtils;
import com.eins.book.store.dao.UserMapper;
import com.eins.book.store.entity.User;
import com.eins.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody User ReqUser, HttpServletRequest httpServletRequest) {
        System.out.println("收到请求");
        Object user = userService.login(ReqUser.getUsername(), ReqUser.getPassword());
        if(user == null) {
            return new ResponseEntity("Username not exits", HttpStatus.BAD_REQUEST);
        }
        else {
            if(user.equals("Wrong password!")) {
                return new ResponseEntity("Wrong password!", HttpStatus.BAD_REQUEST);
            }
            else {
                httpServletRequest.getSession().setAttribute(ConstantUtils.SESSION_USER, user);
                return new ResponseEntity("OK", HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ResponseEntity login(HttpServletRequest httpServletRequest) {
        System.out.println("收到请求");
        User user = (User) httpServletRequest.getSession().getAttribute(ConstantUtils.SESSION_USER);
        //已登录
        if(user != null) {
            return new ResponseEntity("OK", HttpStatus.OK);
        }

        return new ResponseEntity("Please login in!", HttpStatus.UNAUTHORIZED);
    }
}
