package com.musiva.security.security.user.services;

import com.musiva.security.security.user.Authority;
import com.musiva.security.security.user.repositories.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAuthorityService implements AuthorityService {

    public static final String DEFAULT_AUTHORITY = "USER";
    private final AuthorityRepository authorityRepository;

    public DefaultAuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Optional<Authority> findDefaultAuthority() {
        return authorityRepository.findAuthorityByPermission(DEFAULT_AUTHORITY);
    }

}
