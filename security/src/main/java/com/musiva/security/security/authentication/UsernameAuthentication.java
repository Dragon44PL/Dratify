package com.musiva.security.security.authentication;

import com.musiva.security.security.responses.UsernameResponse;
import com.musiva.security.web.models.UsernameRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UsernameAuthentication implements RequestAuthentication<UsernameRequest, UsernameResponse> {

    private final AuthenticationManager authenticationManager;

    public UsernameAuthentication(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public UsernameResponse authenticate(UsernameRequest usernameRequest) {
        String username = usernameRequest.getPrincipal();
        String password = usernameRequest.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        String usernameResponse = username;

        try {
            authenticationManager.authenticate(token);
        } catch(BadCredentialsException e) {
           usernameResponse = null;
        }

        return new UsernameResponse(usernameResponse);
    }

}
