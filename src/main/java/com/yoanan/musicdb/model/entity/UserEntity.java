package com.yoanan.musicdb.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class UserEntity extends BaseEntity {

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles;

    public UserEntity() {
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }
}
