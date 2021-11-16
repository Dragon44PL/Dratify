package com.musiva.security.web.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.musiva.security.security.requests.CredentialsRequest;

@JsonIgnoreProperties
public class UsernameRequest implements CredentialsRequest {

    private String username;
    private String password;

    public UsernameRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private UsernameRequest() {
        this.username = "";
        this.password = "";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("username")
    public String getPrincipal() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

}
