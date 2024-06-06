package com.peppermintflowers.poc.user_management.config;

import com.peppermintflowers.poc.user_management.repository.UserRepository;
import com.peppermintflowers.poc.user_management.service.TokenHandlerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.SecurityFilterChain;
import com.peppermintflowers.poc.user_management.config.JwtAuthenticationFilter;
import com.peppermintflowers.poc.user_management.config.JwtGenerationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;
    private final TokenHandlerImpl tokenHandler;

    private static final String[] AUTH_WHITELIST = {
            // for Swagger UI v2
            "/v2/api-docs",
            "/swagger-ui.html",
            "swagger-ui/index.html",
            "swagger-ui/index.html/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/v3/api-docs/**",
            "/v3/api-docs/",
            "/swagger-ui/**",
            "api/v1/auth/**",
            "api/v1/dashboard/products"
    };


    @Bean
    UserDetailsService userDetailsService() {
        return username-> userRepository.findUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(tokenHandler, userDetailsService());
    }

    @Bean
    JwtGenerationFilter jwtGenerationFilter(AuthenticationConfiguration config) throws Exception {

        JwtGenerationFilter jwtGenerationFilter = new JwtGenerationFilter(tokenHandler, authenticationManager(config));
        jwtGenerationFilter.setAuthenticationManager(authenticationManager(config));
        return jwtGenerationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration config) throws Exception {

        http.csrf()
                .disable()
                .authorizeRequests()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtGenerationFilter(config))
                .addFilterBefore(jwtAuthenticationFilter(), JwtGenerationFilter.class );


        return http.build();
    }
}