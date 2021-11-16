package com.musiva.security.security.user.services;

import com.musiva.security.security.authentication.AuthenticatedUser;
import com.musiva.security.security.user.User;
import com.musiva.security.security.user.repositories.UserRepository;
import com.musiva.security.util.mappers.UserMapper;
import com.musiva.security.web.models.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final AuthenticatedUser authenticatedUser;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public DefaultUserService(UserRepository userRepository, AuthenticatedUser authenticatedUser, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticatedUser = authenticatedUser;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserDto> findUser(String username) {
        return userMapper.convertTo(Objects.requireNonNull(userRepository.findUserByUsername(username).orElse(null)));
    }

    @Override
    public Optional<UserDto> returnAuthenticatedCredentials() {
        Optional<org.springframework.security.core.userdetails.User> user = authenticatedUser.findAuthenticatedUser();
        return user.flatMap(this::processConvertingUser);
    }

    private Optional<UserDto> processConvertingUser(org.springframework.security.core.userdetails.User user) {
        Optional<GrantedAuthority> authority = user.getAuthorities().stream().findFirst();
        return Optional.of(new UserDto(user.getUsername(), user.isEnabled(), authority.get().getAuthority()));
    }

    @Override
    public Optional<UserDto> createUser(UserDto user) {
        if(checkUserData(user)) {
            String encoded = passwordEncoder.encode(user.getPassword());
            user.setPassword(encoded);
            Optional<User> converted = userMapper.convertFrom(user);
            System.out.println("test");
            return userMapper.convertTo(converted.map(userRepository::save).get());
        }

        return Optional.empty();
    }

    private boolean checkUserData(UserDto userDTO) {
        return (userDTO != null && userDTO.getPassword() != null && !userDTO.getPassword().equals("") )
                && userDTO.getUsername() != null && !userDTO.getUsername().equals("") && !userExists(userDTO.getUsername());
    }

    private boolean userExists(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }
}
