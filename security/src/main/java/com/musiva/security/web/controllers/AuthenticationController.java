package com.musiva.security.web.controllers;

import com.musiva.security.security.services.AuthenticationRequestService;
import com.musiva.security.web.models.AuthenticationResponse;
import com.musiva.security.web.models.UsernameRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class AuthenticationController {

    private final AuthenticationRequestService<UsernameRequest> authenticationRequestService;

    public AuthenticationController(AuthenticationRequestService<UsernameRequest> authenticationRequestService) {
        this.authenticationRequestService = authenticationRequestService;
    }

    @PostMapping(value = "/api/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UsernameRequest request) {
        Optional<String> token = authenticationRequestService.generateToken(request);
        return token.map(this::processResponseToken).orElseGet(this::processErrorMessage);
    }

    private ResponseEntity<AuthenticationResponse> processResponseToken(String token) {
        AuthenticationResponse response = new AuthenticationResponse(token);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<AuthenticationResponse> processErrorMessage() {
        AuthenticationResponse response = new AuthenticationResponse("Bad Credentials provided");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
