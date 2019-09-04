package com.eins.book.store.commons;

import com.eins.book.store.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常量工具类
 */
public class ConstantUtils {
    public static Map<String, Long> adminLoginMap = new ConcurrentHashMap<String, Long>();
    public static Map<String, Long> userLoginMap = new ConcurrentHashMap<String, Long>();
    public static Boolean productionTip = false;
}