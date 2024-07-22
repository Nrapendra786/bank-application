package com.nrapendra.bank.security;

import java.util.Collections;
import java.util.Arrays;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import com.nrapendra.bank.security.filters.JwtGenerationFilter;
import com.nrapendra.bank.security.filters.JwtValidationFilter;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nrapendra.bank.security.filters.CsrfCookieFilter;
import com.nrapendra.bank.security.filters.AfterSigninLoggingFilter;

@Configuration
public class SecurityConfig {

        private static final String[] PUBLIC_ROUTES = {
                        "/api/v1/auth/signup",
                        "/api/v1/auth/signin",
                        "/api/v1/messages",
                        "/api/v1/notifications"
        };

        private static final String[] PROTECTED_ROUTES = {
                        "/api/v1/accounts",
                        "/api/v1/transactions",
                        "/api/v1/loans",
                        "/api/v1/cards",
                        "/api/v1/customers"
        };

        private static final String[] ADMIN_ONLY_ROUTES = {
                        "/api/v1/dashboard"
        };

        private final SecretKey signingKey;

        private CorsConfiguration corsHandler() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);
                return config;
        }

        private CsrfTokenRequestAttributeHandler csrfHandler() {
                CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
                requestHandler.setCsrfRequestAttributeName("_csrf");
                return requestHandler;
        }

        public SecurityConfig(@Value("${security.jwt.token.secret-key:secret-key}") String secret) {
                this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .cors(corsCustomizer -> corsCustomizer
                                                .configurationSource(request -> corsHandler()))
                                .csrf((csrf) -> csrf.csrfTokenRequestHandler(csrfHandler())
                                                .ignoringRequestMatchers(PUBLIC_ROUTES)
                                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                                .addFilterAfter(new AfterSigninLoggingFilter(), BasicAuthenticationFilter.class)
                                .addFilterAfter(new JwtGenerationFilter(signingKey), BasicAuthenticationFilter.class)
                                .addFilterBefore(new JwtValidationFilter(signingKey), JwtGenerationFilter.class)
                                .authorizeHttpRequests((request) -> request
                                                .requestMatchers(ADMIN_ONLY_ROUTES).hasRole("ADMIN")
                                                .requestMatchers(PROTECTED_ROUTES).hasAnyRole("USER", "ADMIN")
                                                .requestMatchers(PROTECTED_ROUTES).authenticated()
                                                .requestMatchers(PUBLIC_ROUTES).permitAll())
                                .formLogin(Customizer.withDefaults())
                                .httpBasic(Customizer.withDefaults());
                return http.build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

}
