package com.musiva.security.web.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {

    private String username;

    @JsonIgnore
    @JsonProperty
    private String password;

    private boolean isEnabled;
    private String role;

    public UserDto(String username, boolean isEnabled, String role) {
        this.username = username;
        this.isEnabled = isEnabled;
        this.role = role;
    }

    public UserDto() { }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
