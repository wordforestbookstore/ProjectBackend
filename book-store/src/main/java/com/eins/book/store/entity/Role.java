package com.eins.book.store.entity;

import javax.persistence.*;

@Table(name = "bookstoredatabase.role")
public class Role {
    @Id
    @Column(name = "role_id")
    private Integer roleId;

    private String name;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}