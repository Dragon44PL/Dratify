package com.musiva.security.security.services;

import com.musiva.security.security.requests.CredentialsRequest;

import java.util.Optional;

public interface AuthenticationRequestService<T extends CredentialsRequest> {
    Optional<String> generateToken(T credentialsRequest);
}
