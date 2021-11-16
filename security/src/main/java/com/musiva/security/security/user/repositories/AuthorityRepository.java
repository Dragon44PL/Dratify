package com.musiva.security.security.user.repositories;

import com.musiva.security.security.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findAuthorityByPermission(String permission);
}
