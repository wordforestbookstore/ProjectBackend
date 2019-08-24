package com.eins.book.store.commons;

import org.springframework.stereotype.Component;

/**
 * Created by qhong on 2018/10/15 15:46
 **/
@Component
public class CookieUtils {

    public static boolean CookieConfirm(String id) {
        if(id != null) {
            return true;
        }
        return false;
    }
}
