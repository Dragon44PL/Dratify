package com.musiva.security.web.models;

public class AuthenticationResponse {

    private String response;

    public AuthenticationResponse(String response) {
        this.response = response;
    }

    private AuthenticationResponse() {}

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}