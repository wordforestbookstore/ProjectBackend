package com.eins.book.store.controller;

import com.eins.book.store.dao.UserMapper;
import com.eins.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

/**
 * 登录控制器
 */
@RestController
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody User ReqUser) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", ReqUser.getUsername());
        User user = userMapper.selectOneByExample(example);
        System.out.println("收到请求");
        if(user == null) {
            return new ResponseEntity("Username not exits", HttpStatus.BAD_REQUEST);
        }
        else {
            if(user.getPassword().equals(ReqUser.getPassword())) {
                return new ResponseEntity("OK", HttpStatus.OK);
            }
            else {
                return new ResponseEntity("Wrong password!", HttpStatus.BAD_REQUEST);
            }
        }
    }
}
