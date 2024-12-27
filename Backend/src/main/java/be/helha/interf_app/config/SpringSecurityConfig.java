package be.helha.interf_app.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import be.helha.interf_app.security.JWTFilter;
/**
 * Spring Security configuration class.
 *
 * This class defines:
 * - Access rules for various resources (URLs)
 * - CSRF disabling for the application
 * - Integration of the JWT filter for authentication
 * - CORS permissions configuration for requests from specific sources
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    /**
     * JWT filter used to validate authentication tokens.
     */
    @Autowired
    JWTFilter jwtFilter;

    /**
     * Configures the Spring security filter chain.
     *
     * @param http the HttpSecurity object used to define security rules
     * @return the configured SecurityFilterChain object
     * @throws Exception in case of configuration errors
     */
    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests -> {

                    authorizeRequests.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

                    // Group-related permissions
                    authorizeRequests.requestMatchers(HttpMethod.GET,"/api/groups/**").hasRole("User");
                    authorizeRequests.requestMatchers(HttpMethod.POST,"/api/groups/**").hasRole("User");
                    authorizeRequests.requestMatchers(HttpMethod.PUT,"/api/groups/**")
                            .access("@securityService.checkOwnerGroupAccess(authentication)");
                    authorizeRequests.requestMatchers(HttpMethod.DELETE,"/api/groups/**")
                            .access("@securityService.checkOwnerGroupAccess(authentication)");

                    // Form-related permissions
                    authorizeRequests.requestMatchers("/api/forms/**").hasRole("User");
                    // Admin-only endpoints
                    authorizeRequests.requestMatchers("/api/admin").hasRole("Admin");
                    // User management permissions for Admin
                    authorizeRequests.requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("Admin");
                    authorizeRequests.requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("Admin");
                    authorizeRequests.requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("Admin");
                    authorizeRequests.requestMatchers("/api/forms/**").hasRole("Admin");

                    authorizeRequests.requestMatchers("/swagger-ui/**","/v3/api-docs","/api/users/**").permitAll();

                    authorizeRequests.anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }


    /**
     * Configures Cross-Origin Resource Sharing (CORS) settings.
     *
     * @return the configured CorsConfigurationSource object
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
