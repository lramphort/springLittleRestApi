package com.springapp.restfull.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Profile("dev")
    @Bean
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http, AccessDeniedHandler myAccessDeniedHandler) throws Exception {
        http
            .csrf().disable()
            .cors().disable();

        return http.build();
    }

    @Profile("prod")
    @Bean
    public SecurityFilterChain prodSecurityFilterChain(HttpSecurity http, AccessDeniedHandler myAccessDeniedHandler) throws Exception {
        http
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler)
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .cors()
                .configurationSource(corsConfigurationSource());

        return http.build();
    }

    @Profile("prod")
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Permettre à tous les domaines d'accéder à l'API
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-XSRF-TOKEN"));
        configuration.setExposedHeaders(Arrays.asList("X-XSRF-TOKEN"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AccessDeniedHandler myAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    public static class CustomAccessDeniedHandler implements AccessDeniedHandler {

        private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response,
                           AccessDeniedException accessDeniedException)
                throws IOException, ServletException {

            logger.warn("Accès refusé : {} - {}", request.getRequestURI(), accessDeniedException.getMessage());
            // Rediriger l'utilisateur vers une page d'erreur spécifique
        }
    }

}
