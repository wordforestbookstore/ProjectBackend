package com.eins.book.store.controller;

import com.eins.book.store.commons.ConstantUtils;
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


    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity login(String cookie, Boolean admin, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long userid = null;
        if(CookieUtils.CookieConfirm(cookie)) {
            if(admin == true) {
                if(ConstantUtils.adminLoginMap.get(cookie) == null) {
                    httpServletResponse.setContentType("text/plain");
                    return new ResponseEntity("Please login!", HttpStatus.BAD_REQUEST);
                }
                else {
                    userid = ConstantUtils.adminLoginMap.get(cookie);
                    httpServletResponse.setContentType("application/json");
                    return new ResponseEntity(userid, HttpStatus.OK);
                }
            }
            else {
                if(ConstantUtils.userLoginMap.get(cookie) == null) {
                    httpServletResponse.setContentType("text/plain");
                    return new ResponseEntity("Please login!", HttpStatus.BAD_REQUEST);
                }
                else {
                    userid = ConstantUtils.userLoginMap.get(cookie);
                    httpServletResponse.setContentType("application/json");
                    return new ResponseEntity(userid, HttpStatus.OK);
                }
            }
        }

        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("Please login!", HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody User ReqUser, Boolean admin, HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest) {
        System.out.println(admin);
        if(admin == true) {
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

                        ConstantUtils.adminLoginMap.put(cookie, ((User) user).getId());
                        Map<String, Object> mp = new HashMap<String, Object>();
                        ((User) user).setPassword(null);
                        mp.put("userInfo", user);
                        mp.put("cookieID", cookie);

                        httpServletResponse.setContentType("application/json");
                        return new ResponseEntity(mp, HttpStatus.OK);
                    }
                }
            }
            else {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Yor are not a admin!", HttpStatus.BAD_REQUEST);
            }
        }
        else {
            if(!userService.checkUserAdmin(ReqUser.getUsername())) {
                Object user = userService.login(ReqUser.getUsername(), ReqUser.getPassword());
                if (user == null) {
                    httpServletResponse.setContentType("text/plain");
                    return new ResponseEntity("Username not exits!", HttpStatus.BAD_REQUEST);
                } else {
                    if (user.equals("Wrong password!")) {
                        httpServletResponse.setContentType("text/plain");
                        return new ResponseEntity("Wrong password!", HttpStatus.BAD_REQUEST);
                    } else {
                        User loginUser = (User) user;
                        String cookie = new EncryptUtil().DESencode(loginUser.getUsername() + ":" + DateUtils.getStringDate(), "Salt");

                        System.out.println(cookie);

                        ConstantUtils.userLoginMap.put(cookie, ((User) user).getId());
                        Map<String, Object> mp = new HashMap<String, Object>();
                        ((User) user).setPassword(null);
                        mp.put("userInfo", user);
                        mp.put("cookieID", cookie);

                        httpServletResponse.setContentType("application/json");
                        return new ResponseEntity(mp, HttpStatus.OK);
                    }
                }
            }
            else {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Yor are not a user!", HttpStatus.BAD_REQUEST);
            }
        }
    }


    @CrossOrigin
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity logout(String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (ConstantUtils.adminLoginMap.get(cookie) == null) {
            if(ConstantUtils.userLoginMap.get(cookie) == null) {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Cookie not Exit!", HttpStatus.BAD_REQUEST);
            }
            else {
                ConstantUtils.userLoginMap.remove(cookie);
            }
        }
        else {
            ConstantUtils.adminLoginMap.remove(cookie);
        }

        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("Succeessful Exit!", HttpStatus.OK);
    }
}
