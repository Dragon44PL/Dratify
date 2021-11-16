package com.musiva.security.security.services;

import com.musiva.security.security.authentication.RequestAuthentication;
import com.musiva.security.security.responses.UsernameResponse;
import com.musiva.security.util.JwtUtilities;
import com.musiva.security.web.models.UsernameRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAuthenticationRequestService implements AuthenticationRequestService<UsernameRequest> {

    private final RequestAuthentication<UsernameRequest, UsernameResponse> requestAuthentication;
    private final JwtUtilities jwtUtilities;

    public DefaultAuthenticationRequestService(RequestAuthentication<UsernameRequest, UsernameResponse> requestAuthentication, JwtUtilities jwtUtilities) {
        this.requestAuthentication = requestAuthentication;
        this.jwtUtilities = jwtUtilities;
    }

    public Optional<String> generateToken(UsernameRequest usernameRequest) {
        UsernameResponse usernameResponse = requestAuthentication.authenticate(usernameRequest);
        return usernameResponse.response().isPresent() ? Optional.of(jwtUtilities.createToken(usernameResponse.response().get())) : Optional.empty();
    }
}
