package com.eins.book.store.controller;

import com.eins.book.store.commons.*;
import com.eins.book.store.entity.User;
import com.eins.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Controller
public class PasswordRecoveryController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createrecovery", method = RequestMethod.POST)
    public ResponseEntity createPasswordRecovery(@RequestBody User ReqUser, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        String email = ReqUser.getEmail();
        if(userService.checkUserEmailExist(email)) {
            /*Token操作*/
            String token = UUID.randomUUID().toString();

            /*生成验证密码并发邮件*/
            String initpassword = InitPassWordUtils.getRandomString(16);
            new EmailUtils().sendcreatepassword(email, token, initpassword);
            Long id = userService.getIdByEmail(email);
            User user = userService.getUserById(id);
            user.setPassword(initpassword);
            userService.updateUser(user);
            userService.insertToken(token, user, DateUtils.getNowDate(),"recovery");
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("This email is not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/recovery", method = RequestMethod.POST)
    public ResponseEntity passwordRecovery (@RequestBody User ReqUser, String currentPassword , HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        // User user = userService.getUserById();
        if(userService.confirmPassword(currentPassword, ReqUser)) {
            ReqUser.setPassword(EncryptUtil.getSha256(ReqUser.getPassword()));
            Long id = userService.getIdByName(ReqUser.getUsername());
            User user = userService.getUserById(id);
            ReqUser.setId(id);
            if(!user.getEnabled()) userService.eableUser(ReqUser);
            else userService.updateUser(ReqUser);
            userService.delToken(id);
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("passwordRecovery Successfully", HttpStatus.OK);
        }
        else {
            httpServletResponse.setContentType("text/plain");
            return new ResponseEntity("Wrong CurrentPassword", HttpStatus.BAD_REQUEST);
        }
    }
}
