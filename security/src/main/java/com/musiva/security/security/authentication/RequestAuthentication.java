package com.musiva.security.security.authentication;


import com.musiva.security.security.requests.CredentialsRequest;
import com.musiva.security.security.responses.AuthenticationResponse;

public interface RequestAuthentication<T extends CredentialsRequest, C extends AuthenticationResponse<?>> {
   C authenticate(T request);
}
