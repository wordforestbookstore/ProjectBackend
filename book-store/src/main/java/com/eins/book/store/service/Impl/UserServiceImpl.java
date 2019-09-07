package com.eins.book.store.service.Impl;

import com.eins.book.store.commons.EncryptUtil;
import com.eins.book.store.dao.*;
import com.eins.book.store.entity.*;
import com.eins.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
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

    @Autowired
    private TokenKindMapper tokenKindMapper;

    @Autowired
    private CartItemMapper cartItemMapper;


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
            return false;
        }
        else {
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
            return false;
        }
        else {
            return true;
        }
    }

    /*修改密码时 确认密码和原密码是否相同*/
    @Override
    public boolean confirmPassword(String currentPassword, User user){
        String username = user.getUsername();
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        User user2 = userMapper.selectOneByExample(example);
        if(user2.getPassword().equals(currentPassword)) {
            return true;
        }
        else {
            return false;
        }
    }

    /*插入Token*/
    @Override
    public void insertToken(String token, User user, Date time, String kind) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUserId(user.getId());
        passwordResetToken.setExpiryDate(time);
        passwordResetTokenMapper.insert(passwordResetToken);

        TokenKind tokenKind = new TokenKind();
        tokenKind.setToken(token);
        tokenKind.setKind(kind);
        tokenKindMapper.insert(tokenKind);
    }

    /*删除token*/
    @Override
    public boolean delToken(Long id) {

        Example example = new Example(PasswordResetToken.class);
        example.createCriteria().andEqualTo("userId", id);
        PasswordResetToken passwordResetToken = passwordResetTokenMapper.selectOneByExample(example);
        if (passwordResetToken == null) {
            return false;
        }
        else {

            passwordResetTokenMapper.delete(passwordResetToken);

            Example example1 = new Example(TokenKind.class);
            example1.createCriteria().andEqualTo("token", passwordResetToken.getToken());
            TokenKind tokenKind = tokenKindMapper.selectOneByExample(example1);
            tokenKindMapper.delete(tokenKind);
            return true;
        }
    }

    /*通过token或者其类型*/
    @Override
    public String getKindByToken(String token) {
        Example example  = new Example(TokenKind.class);
        example.createCriteria().andEqualTo("token", token);
        TokenKind tokenKind = tokenKindMapper.selectOneByExample(example);
        if(tokenKind == null) {
            return null;
        }
        else {
            return tokenKind.getKind();
        }
    }

    /*通过token找到userid*/
    @Override
    public Long FindUserToken(String token) {
        Example example = new Example(PasswordResetToken.class);
        example.createCriteria().andEqualTo("token", token);
        PasswordResetToken passwordResetToken = passwordResetTokenMapper.selectOneByExample(example);
        if (passwordResetToken == null) {
            return 0l;
        }
        else {
            return passwordResetToken.getUserId();
        }
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
        User user2 = userMapper.selectOneByExample(example);
        user2.setEnabled(true);
        user2.setPassword(user.getPassword());
        user2.setEmail(user.getEmail());
        user2.setFirstName(user.getFirstName());
        user2.setLastName(user.getLastName());
        user2.setPhone(user.getPhone());
        userMapper.updateByPrimaryKeySelective(user2);
        /*将用户角色表插入*/
        UserRole userRole = new UserRole();
        userRole.setRoleId(1);
        userRole.setUserId(user2.getId());
        userRoleMapper.insert(userRole);
        /*为该用户初始化一个购物车插入*/
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(user2.getId());
        BigDecimal tmp = new BigDecimal(0.00);
        shoppingCart.setGrandTotal(tmp);
        shoppingCartMapper.insert(shoppingCart);
    }



    /*通过id找到user对象*/
    @Override
    public User getUserById(Long id) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", id);
        User user = userMapper.selectOneByExample(example);
        if(user == null) {
            return null;
        }
        else {
            return user;
        }
    }


    /*通过用户名获得用户ID*/
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

    /*通过用户邮箱找到该用户id*/
    @Override
    public  Long getIdByEmail(String email) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("email", email);
        User user = userMapper.selectOneByExample(example);
        if (user == null) {
            return 0l;
        }
        else {
            return user.getId();
        }
    }

    /*修改用户信息*/
    @Override
    public void updateUser(User user) {
        if(user.getId() == null) user.setId(getIdByName(user.getUsername()));
        userMapper.updateByPrimaryKeySelective(user);
    }
}
