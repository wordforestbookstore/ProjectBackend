package com.eins.book.store.entity;

import javax.persistence.*;

@Table(name = "bookstoredatabase.user_role")
public class UserRole {
    @Id
    @Column(name = "user_role_id")
    private Long userRoleId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "user_id")
    private Long userId;

    /**
     * @return user_role_id
     */
    public Long getUserRoleId() {
        return userRoleId;
    }

    /**
     * @param userRoleId
     */
    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    /**
     * @return role_id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}