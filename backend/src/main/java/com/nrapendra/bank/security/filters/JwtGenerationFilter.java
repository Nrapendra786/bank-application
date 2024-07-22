package com.nrapendra.bank.security.filters;

import java.util.Date;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

@Component
public class JwtGenerationFilter extends OncePerRequestFilter {

    private final SecretKey signingKey;
    private final Long jwtExpiration = 86_400_000L; // 1 day

    public JwtGenerationFilter(SecretKey signingKey) {
        this.signingKey = signingKey;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String jwt = Jwts.builder()
                    .issuer("Spring Bank")
                    .subject("JWT")
                    .claim("username", authentication.getName())
                    .claim("authorities", getAuthorities(authentication.getAuthorities()))
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(signingKey)
                    .compact();
            response.setHeader("Authorization", jwt);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return !request.getServletPath().equals("/api/v1/auth/signin");
    }

    private String getAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection)
            authoritiesSet.add(authority.getAuthority());
        return String.join(",", authoritiesSet);
    }

}
