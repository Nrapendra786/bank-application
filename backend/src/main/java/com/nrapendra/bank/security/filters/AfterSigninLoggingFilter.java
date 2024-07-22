package com.nrapendra.bank.security.filters;

import java.util.logging.Logger;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AfterSigninLoggingFilter implements Filter {

    private final Logger logger = Logger.getLogger(AfterSigninLoggingFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            logger.info("User" + authentication.getName() + " has successfully signed in and "
                    + " has the following authorities: " + authentication.getAuthorities().toString());

        filterChain.doFilter(request, response);
    }

}
