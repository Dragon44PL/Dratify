package com.musiva.security.security.user;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "AUTHORITIES")
public class Authority  {

    @Id
    @GeneratedValue
    private UUID id;

    public Authority(String permission, Set<User> users) {
        super();
        this.permission = permission;
        this.users = users;
    }

    public Authority(String permission) {
        this.permission = permission;
        this.users = new HashSet<>();
    }

    public Authority() { }

    private String permission;

    @ManyToMany(mappedBy = "authorities", cascade = CascadeType.MERGE)
    private Set<User> users;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public Set<User> getUsers() {
        return users;
    }
}