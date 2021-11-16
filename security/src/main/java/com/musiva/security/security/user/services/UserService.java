package com.musiva.security.security.user.services;

import com.musiva.security.web.models.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> findUser(String username);
    Optional<UserDto> createUser(UserDto userDTO);
    Optional<UserDto> returnAuthenticatedCredentials();
}
