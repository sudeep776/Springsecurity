package com.spring_security.security.Filter;

import jakarta.servlet.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.logging.Logger;
public class AuthoritiesLoggingFilter implements Filter {
    private final Logger LOG=Logger.getLogger(AuthoritiesLoggingFilter.class.getName());
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null!=authentication){
            LOG.info("User "+authentication.getName()+" is successfull authenticated");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
