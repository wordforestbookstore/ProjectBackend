package com.eins.book.store.service;

import com.eins.book.store.entity.User;

public interface UserService {
    public Object login(String username, String password);
    public boolean checkUserAdmin(String username);
}
