package com.musiva.security.configuration.security;

import com.musiva.security.configuration.security.filters.JwtTokenFilter;
import com.musiva.security.security.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

//    @Autowired
//    private BasicCorsFilter basicCorsFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //http.addFilterBefore(basicCorsFilter, JwtTokenFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
            .authorizeRequests()
                .antMatchers("/api/authenticate**")
                .permitAll()
                .and()
            .authorizeRequests()
                .antMatchers("/api/messages**")
                .authenticated()
                .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/users/user**")
                .permitAll()
                .and()
            .authorizeRequests()
                .antMatchers("/h2-console/**")
                .permitAll()
                .and()
            .authorizeRequests()
                .anyRequest()
                .authenticated();

        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
