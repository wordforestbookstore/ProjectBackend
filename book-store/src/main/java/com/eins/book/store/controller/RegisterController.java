package com.eins.book.store.controller;

import com.eins.book.store.commons.*;
import com.eins.book.store.entity.User;
import com.eins.book.store.service.UserService;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class RegisterController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createregister", method = RequestMethod.POST)
    public ResponseEntity createrRegister(@RequestBody User ReqUser, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (!userService.checkUserNameExist(ReqUser.getUsername()) && !userService.checkUserEmailExist(ReqUser.getEmail())) {
            String token = UUID.randomUUID().toString();

            String initpassword = InitPassWordUtils.getRandomString(16);
            new  EmailUtils().sendcreate(ReqUser.getEmail(), token, initpassword);
            ReqUser.setPassword(initpassword);
            ReqUser.setEnabled(false);

            userService.InsertUser(ReqUser);
            userService.insertToken(token, ReqUser, DateUtils.getNowDate(), "register");

            return new ResponseEntity(HttpStatus.OK);
        }
        else if (userService.checkUserNameExist(ReqUser.getUsername())) {
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Your username has been registered", HttpStatus.BAD_REQUEST);
        }
        else {
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Your email has been registered", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "newUser/{token}", method = RequestMethod.GET)
    public ResponseEntity verify(@PathVariable("token") String token, HttpServletResponse httpServletResponse) {
        if (userService.FindUserToken(token) == 0) {
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Your token is invaild", HttpStatus.BAD_REQUEST);
        }
        else {
            Long id = userService.FindUserToken(token);
            User user = userService.getUserById(id);

            Map<String, String> mp = new HashMap<String, String>();
            mp.put("username", user.getUsername());
            mp.put("email", user.getEmail());
            mp.put("firstname", user.getFirstName());
            mp.put("lastname", user.getLastName());
            mp.put(token, userService.getKindByToken(token));
            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(mp, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register (@RequestBody User ReqUser, String currentPassword , HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if(userService.confirmPassword(currentPassword, ReqUser)) {
            if(ReqUser.getUsername() == null || ReqUser.getUsername() == ""){
                return new ResponseEntity("Your username is null", HttpStatus.BAD_REQUEST);
            }
            ReqUser.setPassword(EncryptUtil.getSha256(ReqUser.getPassword()));
            userService.eableUser(ReqUser);
            httpServletResponse.setContentType("text/plain");
            Long id = userService.getIdByName(ReqUser.getUsername());
            userService.delToken(id);
            return new ResponseEntity("Register Successfully", HttpStatus.OK);
        }
        else {
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Wrong CurrentPassword", HttpStatus.BAD_REQUEST);
        }
    }

}
