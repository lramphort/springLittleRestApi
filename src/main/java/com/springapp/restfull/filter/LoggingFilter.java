package com.springapp.restfull.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class LoggingFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // Log request information
        logger.info("Request Method: {} Request URI: {}", httpRequest.getMethod(), httpRequest.getRequestURI());

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        Enumeration<String> headerNames = req.getHeaderNames();
        System.out.println("---- Request Headers ----");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info("Header: {} : {}", headerName, req.getHeader(headerName));
        }
        System.out.println("-------------------------");


        // Continue processing the request
        filterChain.doFilter(servletRequest, servletResponse);
    }
}