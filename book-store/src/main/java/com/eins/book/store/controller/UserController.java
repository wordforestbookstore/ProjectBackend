package com.eins.book.store.controller;

import com.eins.book.store.commons.ConstantUtils;
import com.eins.book.store.commons.CookieUtils;
import com.eins.book.store.commons.EncryptUtil;
import com.eins.book.store.entity.User;
import com.eins.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ResponseEntity edit(String cookie, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            Long userid = ConstantUtils.userLoginMap.get(cookie);
            User user = userService.getUserById(userid);
            httpServletResponse.setContentType("application/json");
            return new ResponseEntity(user, HttpStatus.OK);
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public ResponseEntity saveEdit(@RequestBody User ReqUser,String cookie, String currentPassword, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (CookieUtils.CookieConfirm(cookie)) {
            currentPassword = EncryptUtil.getSha256(currentPassword);
            if(userService.confirmPassword(currentPassword, ReqUser)) {
                Long id = userService.getIdByName(ReqUser.getUsername());
                User user = userService.getUserById(id);

                if(ReqUser.getPassword() == "" || ReqUser.getPassword() == null) {
                    ReqUser.setPassword(user.getPassword());
                }
                else {
                    ReqUser.setPassword(EncryptUtil.getSha256(ReqUser.getPassword()));
                }
                userService.updateUser(ReqUser);
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("edit Successfully", HttpStatus.OK);
            }
            else {
                httpServletResponse.setContentType("text/plain");
                return new ResponseEntity("Wrong CurrentPassword", HttpStatus.BAD_REQUEST);
            }
        }
        httpServletResponse.setContentType("text/plain");
        return new ResponseEntity("No Permission!", HttpStatus.UNAUTHORIZED);
    }
}
