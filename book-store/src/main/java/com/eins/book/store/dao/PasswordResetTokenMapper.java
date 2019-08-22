package com.eins.book.store.dao;

import com.eins.book.store.entity.PasswordResetToken;
import tk.mybatis.MyMapper;

/**
 * 密码Token DAO
 */
public interface PasswordResetTokenMapper extends MyMapper<PasswordResetToken> {
}