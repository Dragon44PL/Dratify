package com.musiva.security.security.responses;

import java.util.Optional;

public class IdentifierResponse implements AuthenticationResponse<Long> {

    private final Long id;

    public IdentifierResponse(Long id ) {
        this.id = id;
    }

    public Optional<Long> response() {
        return Optional.of(id);
    }

}
