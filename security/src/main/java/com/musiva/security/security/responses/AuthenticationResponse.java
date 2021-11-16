package com.musiva.security.security.responses;

import java.util.Optional;

public interface AuthenticationResponse<T> {
    Optional<T> response();
}