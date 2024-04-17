package com.tom.passwordSafetyBox.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Autoriser les requêtes depuis n'importe quelle origine
        config.addAllowedHeader("*"); // Autoriser les en-têtes dans les requêtes
        config.addAllowedMethod("*"); // Autoriser les méthodes HTTP (GET, POST, PUT, DELETE, etc.)
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
