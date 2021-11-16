package com.musiva.security.configuration.security.filters;

import com.musiva.security.security.services.JpaUserDetailsService;
import com.musiva.security.util.JwtUtilities;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JpaUserDetailsService userDetailsService;
    private final JwtUtilities jwtUtilities;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    public JwtTokenFilter(JpaUserDetailsService userDetailsService, JwtUtilities jwtUtilities) {
        this.userDetailsService = userDetailsService;
        this.jwtUtilities = jwtUtilities;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorize = httpServletRequest.getHeader("Authorization");
        if(checkAuthorizeHeader(authorize)) {
            processAuthorization(authorize, httpServletRequest);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean checkAuthorizeHeader(String authorize) {
        return authorize != null && authorize.startsWith("Bearer ");
    }

    private boolean checTokenData(String token, String username) {
        return token.length() > 0 && username != null && jwtUtilities.validateToken(token, username);
    }

    private void processAuthorization(String authorize, HttpServletRequest request) {
        String token = "";
        String username = "";
        try {
            token = authorize.substring(7);
            username = jwtUtilities.extractUsername(token);
            if (checTokenData(token, username)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken springToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                springToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(springToken);
            }
        }
        catch (MalformedJwtException | SignatureException e ) {
            logger.debug("Token structure not valid: " + token);
        }
        catch(ExpiredJwtException e) {
            logger.debug("Token expired: " + token);
        }
        catch(UsernameNotFoundException e) {
            logger.debug("Username not found: " + username);
        }
    }

}
