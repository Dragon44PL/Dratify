package com.musiva.security.security.authentication;

import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface AuthenticatedUser {
    Optional<User> findAuthenticatedUser();
}