package com.eins.book.store.commons;

import com.eins.book.store.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量工具类
 */
public class ConstantUtils {
    public static Map<String, Long> adminLoginMap = new HashMap<String, Long>();
    public static Map<String, Long> userLoginMap = new HashMap<String, Long>();
    public static Boolean productionTip = false;
}