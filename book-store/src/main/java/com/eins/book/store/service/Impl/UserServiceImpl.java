package com.eins.book.store.service.Impl;

import com.eins.book.store.commons.EncryptUtil;
import com.eins.book.store.dao.UserMapper;
import com.eins.book.store.dao.UserRoleMapper;
import com.eins.book.store.entity.User;
import com.eins.book.store.entity.UserRole;
import com.eins.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

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
}
