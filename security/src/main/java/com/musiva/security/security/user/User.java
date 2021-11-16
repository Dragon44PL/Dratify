package com.musiva.security.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "USERS")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "USERS_AUTHORITIES", joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "id")})
    private Set<Authority> authorities;

    private boolean isExpired;
    private boolean isEnabled;
    private boolean isLocked;
    private boolean isCredentialsExpired;

    public User(String username, String password, Set<Authority> authorities) {
        super();
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isExpired = false;
        this.isEnabled = true;
        this.isLocked = false;
        this.isCredentialsExpired = false;
    }

    protected User() { }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities.stream()
                    .map((authority) -> new SimpleGrantedAuthority(authority.getPermission()))
                    .collect(Collectors.toSet());
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    public void setAccountExpiration(boolean expiration) {
        this.isExpired = expiration;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    public void setAccountLock(boolean isLocked) {
        this.isLocked = isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    public void setCredentialsExpiration(boolean isCredentialsExpired) {
        this.isCredentialsExpired = isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void enable() {
        this.isEnabled = true;
    }

    public void disable() {
        this.isEnabled = false;
    }

}