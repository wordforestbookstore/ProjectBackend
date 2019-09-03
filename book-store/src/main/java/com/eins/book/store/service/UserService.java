package com.eins.book.store.service;

import com.eins.book.store.entity.BookToCartItem;
import com.eins.book.store.entity.CartItem;
import com.eins.book.store.entity.ShoppingCart;
import com.eins.book.store.entity.User;

import java.util.Date;

public interface UserService {
    public Object login(String username, String password);
    public boolean checkUserAdmin(String username);
    /*2019.8.28*/

    /*check*/
    public boolean checkUserNameExist(String username);
    public boolean checkUserEmailExist(String email);
    public boolean confirmPassword(String currentPassword, User user);


    /*Token*/
    public void insertToken(String token, User user, Date time, String kind);
    public boolean delToken(Long id);
    public String getKindByToken(String token);
    public Long FindUserToken(String token);

    /*User*/
    public void InsertUser(User user);
    public void eableUser(User user);
    public User getUserById(Long id);
    public  Long getIdByName(String username);
    public  Long getIdByEmail(String email);
    public void updateUser(User user);


}
