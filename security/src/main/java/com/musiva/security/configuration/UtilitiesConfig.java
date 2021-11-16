package com.musiva.security.configuration;

import com.musiva.security.util.JwtUtilities;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilitiesConfig {

    public static final String DEFAULT_SECRET_KEY = "SECRET_KEY";
    public static final long DEFAULT_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
    public static final SignatureAlgorithm DEFAULT_SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Bean
    public JwtUtilities jwtUtilities()  {
        return new JwtUtilities(DEFAULT_SECRET_KEY, DEFAULT_EXPIRATION_TIME, DEFAULT_SIGNATURE_ALGORITHM);
    }

}
