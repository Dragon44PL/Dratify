package com.musiva.security.security.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultAuthenticatedUser implements AuthenticatedUser {

    public Optional<User> findAuthenticatedUser() {
        try {
            return Optional.ofNullable((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch(Exception e) {
            return Optional.empty();
        }
    }
}
