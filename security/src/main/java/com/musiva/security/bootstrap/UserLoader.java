package com.musiva.security.bootstrap;

import com.musiva.security.security.user.Authority;
import com.musiva.security.security.user.User;
import com.musiva.security.security.user.repositories.AuthorityRepository;
import com.musiva.security.security.user.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Profile("!test")
public class UserLoader implements CommandLineRunner {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;

    UserLoader(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(String... args) {
        if(userRepository.count() == 0) {
            Authority userAuthority = authorityRepository.save(new Authority("USER"));

            User first = new User("user", passwordEncoder.encode("password"), Set.of(userAuthority) );
            User second = new User("second", passwordEncoder.encode("password"), Set.of(userAuthority));
            userRepository.saveAll(List.of(first, second));
        }
        System.out.println("Number of users: " + userRepository.count());
    }


}
