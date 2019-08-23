package com.eins.book.store.service.Impl;

import com.eins.book.store.dao.UserMapper;
import com.eins.book.store.entity.User;
import com.eins.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Object login(String username, String password) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        User user = userMapper.selectOneByExample(example);
        if(user != null) {
            if(password.equals(user.getPassword())) {
                return user;
            }
            else {
                return "Wrong password!";
            }
        }
        return null;
    }
}
