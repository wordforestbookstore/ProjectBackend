package com.eins.book.store.controller;

import com.eins.book.store.commons.CookieUtils;
import com.eins.book.store.commons.DateUtils;
import com.eins.book.store.commons.EncryptUtil;
import com.eins.book.store.entity.User;
import com.eins.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    private Map<String, User> loginmap = new HashMap<String, User>();

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity login(String cookie, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        User user = null;
        if(CookieUtils.CookieConfirm(cookie)) {
            try {
                user = loginmap.get(cookie);
            }
            catch (Exception e) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Please login!", HttpStatus.BAD_REQUEST);
            }
            if(user == null){
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("User not exits!",HttpStatus.BAD_REQUEST);
            }

            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(user, HttpStatus.OK);
        }

        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("Please login!", HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody User ReqUser, HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest) {
        if(userService.checkUserAdmin(ReqUser.getUsername())) {
            Object user = userService.login(ReqUser.getUsername(), ReqUser.getPassword());
            if(user == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Username not exits!", HttpStatus.BAD_REQUEST);
            }
            else {
                if(user.equals("Wrong password!")) {
                    httpServletResponse.setContentType("text/plain");
                    return new ResponseEntity("Wrong password!", HttpStatus.BAD_REQUEST);
                }
                else {
                    User loginUser = (User) user;
                    String cookie = new EncryptUtil().DESencode(loginUser.getUsername() + ":" + DateUtils.getStringDate(), "Salt");

                    System.out.println(cookie);

                    loginmap.put(cookie, (User) user);
                    Map<String, Object> mp = new HashMap<String, Object>();
                    mp.put("userInfo", user);
                    mp.put("cookieID", cookie);

                    httpServletResponse.setContentType("application/json");
                    return new ResponseEntity(mp, HttpStatus.OK);
                }
            }
        }
        else {
            Object user = userService.login(ReqUser.getUsername(), ReqUser.getPassword());
            if(user == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Username not exits!", HttpStatus.BAD_REQUEST);
            }
            else {
                if(user.equals("Wrong password!")) {
                    httpServletResponse.setContentType("text/plain");
                    return new ResponseEntity("Wrong password!", HttpStatus.BAD_REQUEST);
                }
                else {
                    User loginUser = (User) user;
                    String cookie = new EncryptUtil().DESencode(loginUser.getUsername() + ":" + DateUtils.getStringDate(), "Salt");

                    System.out.println(cookie);

                    loginmap.put(cookie, (User) user);
                    Map<String, Object> mp = new HashMap<String, Object>();
                    mp.put("userInfo", user);
                    mp.put("cookieID", cookie);

                    httpServletResponse.setContentType("application/json");
                    return new ResponseEntity(mp, HttpStatus.OK);
                }
            }

        }
    }

    @CrossOrigin
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity logout(String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (loginmap.get(cookie) == null) {
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Cookie not Exit!", HttpStatus.BAD_REQUEST);
        }
        loginmap.remove(cookie);

        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("Succeessful Exit!", HttpStatus.OK);
    }
}
