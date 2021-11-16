package com.musiva.security.security.user.services;

import com.musiva.security.security.user.Authority;

import java.util.Optional;

public interface AuthorityService {
    Optional<Authority> findDefaultAuthority();
}
