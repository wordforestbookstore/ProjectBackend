package com.eins.book.store.service;

import com.eins.book.store.entity.User;

import java.util.Date;

public interface UserService {
    public Object login(String username, String password);
    public boolean checkUserAdmin(String username);
    /*2019.8.28*/
    public boolean checkUserNameExist(String username);
    public boolean checkUserEmailExist(String email);
    public void InsertUser(User user);
    public Long FindUserToken(String token);
    public User getUserById(Long id);
    public void insertToken(String token, User user, Date time);
    public void eableUser(User user);
    public boolean confirmPassword(String currentPassword, User user);
    public boolean delToken(Long id);
    public  Long getIdByName(String username);
}
