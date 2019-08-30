package com.eins.book.store.service.Impl;

import com.eins.book.store.commons.EncryptUtil;
import com.eins.book.store.dao.*;
import com.eins.book.store.entity.PasswordResetToken;
import com.eins.book.store.entity.ShoppingCart;
import com.eins.book.store.entity.User;
import com.eins.book.store.entity.UserRole;
import com.eins.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordResetTokenMapper passwordResetTokenMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    @Override
    public Object login(String username, String password) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        User user = userMapper.selectOneByExample(example);
        if(user != null) {
            if(EncryptUtil.getSha256(password).equals(user.getPassword())) {
                return user;
            }
            else {
                return "Wrong password!";
            }
        }
        return null;
    }

    @Override
    public boolean checkUserAdmin(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        User user = userMapper.selectOneByExample(example);

        Long id = user.getId();
        List<UserRole> userRoles = userRoleMapper.selectAll();
        for (UserRole userRole : userRoles) {
            if(userRole.getUserId() == id) {
                if (userRole.getRoleId() == 0) {
                    return true;
                }
                else break;
            }
        }
        return false;
    }

    /*2019.8.28*/
    /*检测用户名是否已被注册过*/
    @Override
    public boolean checkUserNameExist(String username){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        User user = userMapper.selectOneByExample(example);

        if (user == null) {
            System.out.println("UserName is not registered");
            return false;
        }
        else {
            System.out.println("UserName is registered");
            return true;
        }
    }

    /*检测用户邮箱是否已经注册过*/
    @Override
    public boolean checkUserEmailExist(String email){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("email", email);
        User user = userMapper.selectOneByExample(example);

        if (user == null) {
            System.out.println("email is not registered");
            return false;
        }
        else {
            System.out.println("email is registered");
            return true;
        }
    }

    /*插入Token*/
    @Override
    public void insertToken(String token, User user, Date time) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUserId(user.getId());
        passwordResetToken.setExpiryDate(time);
        passwordResetTokenMapper.insert(passwordResetToken);

    }

    /*插入注册用户*/
    @Override
    public void InsertUser(User user) {
        userMapper.insert(user);

    }
    /*在邮箱验证之后激活注册用户*/
    @Override
    public void eableUser(User user){
        /*将表单中获得的user按id在数据库中找到user2， 状态设置成激活之后插入*/
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", user.getUsername());
        System.out.println("userName: " + user.getUsername());
        User user2 = userMapper.selectOneByExample(example);
        user2.setEnabled(true);
        user2.setPassword(user.getPassword());
        userMapper.updateByPrimaryKeySelective(user2);
        /*将用户角色表插入*/
        UserRole userRole = new UserRole();
        userRole.setRoleId(1);
        userRole.setUserId(user2.getId());
        userRoleMapper.insert(userRole);
        /*为该用户初始化一个购物车插入*/
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(user2.getId());
        shoppingCart.setGrandTotal(null);
        shoppingCartMapper.insert(shoppingCart);


    }


    @Override
    public Long FindUserToken(String token) {
        Example example = new Example(PasswordResetToken.class);
        example.createCriteria().andEqualTo("token", token);
        PasswordResetToken passwordResetToken = passwordResetTokenMapper.selectOneByExample(example);
        if (passwordResetToken == null) {
            System.out.println("Token is  invalid");
            return 0l;
        }
        else {

            System.out.println("Find Token is " + passwordResetToken.getUserId());
            return passwordResetToken.getUserId();
        }
    }

    @Override
    public User getUserById(Long id) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", id);
        User user = userMapper.selectOneByExample(example);
        if(user == null) {
            System.out.println("Not Find user");
            return null;
        }
        else {
            System.out.println("Find User");
            return user;
        }
    }

    /*修改密码时 确认密码和原密码是否相同*/
    @Override
    public boolean confirmPassword(String currentPassword, User user){
        String username = user.getUsername();
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        User user2 = userMapper.selectOneByExample(example);
        System.out.println("currentPassword " + currentPassword + " user2 word: " + user2.getPassword());
        if(user2.getPassword().equals(currentPassword)) {
            System.out.println("Password is same");
            return true;
        }
        else {
            System.out.println("Password is not same");
            return false;
        }
    }
    @Override
    public  Long getIdByName(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        User user = userMapper.selectOneByExample(example);
        if (user == null) {
            return 0l;
        }
        else {
            return user.getId();
        }
    }
    /*删除token*/
    @Override
    public boolean delToken(Long id) {
        Example example = new Example(PasswordResetToken.class);
        example.createCriteria().andEqualTo("userId", id);
        PasswordResetToken passwordResetToken = passwordResetTokenMapper.selectOneByExample(example);
        if (passwordResetToken == null) {
            System.out.println("该token不存在");
            return false;
        }
        else {
            passwordResetTokenMapper.delete(passwordResetToken);
            System.out.println("删除成功");
            return true;
        }
    }
}
