package com.musiva.security.util.mappers;

import com.musiva.security.security.user.Authority;
import com.musiva.security.security.user.User;
import com.musiva.security.security.user.services.AuthorityService;
import com.musiva.security.web.models.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class UserMapper {

    private Authority authority;
    private final AuthorityService authorityService;

    public UserMapper(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    public Optional<UserDto> convertTo(org.springframework.security.core.userdetails.User user) {
        Optional<GrantedAuthority> authority = user.getAuthorities().stream().findFirst();
        return Optional.of(new UserDto(user.getUsername(), user.isEnabled(), authority.get().getAuthority()));
    }

    public Optional<UserDto> convertTo(User user) {
        Optional<GrantedAuthority> authority = user.getAuthorities().stream().findFirst();
        return Optional.of(new UserDto(user.getUsername(), user.isEnabled(), authority.get().getAuthority()));
    }

    public Optional<User> convertFrom(UserDto user) {
        checkAuthority();
        return Optional.of(new User(user.getUsername(),
                           user.getPassword(),
                           Set.of(authority)));
    }

    private void checkAuthority() {
        if(authority == null) {
            this.authority = authorityService.findDefaultAuthority().get();
        }
    }

}
