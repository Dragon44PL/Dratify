package com.musiva.security.security.requests;

public interface CredentialsRequest {
    String getPrincipal();
    String getPassword();
}
