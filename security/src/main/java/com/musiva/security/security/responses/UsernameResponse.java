package com.musiva.security.security.responses;

import java.util.Optional;

public class UsernameResponse implements AuthenticationResponse<String> {

    private final String username;

    public UsernameResponse(String username) {
        this.username = username;
    }

    public Optional<String> response() {
        return Optional.ofNullable(username);
    }

}
