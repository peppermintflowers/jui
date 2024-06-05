package com.peppermintflowers.poc.user_management.config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peppermintflowers.poc.user_management.model.User;
import com.peppermintflowers.poc.user_management.token.ResponseToken;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.swing.text.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.peppermintflowers.poc.user_management.service.TokenHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Component
@RequiredArgsConstructor
@Import(ApplicationConfig.class)
public class JwtGenerationFilter extends UsernamePasswordAuthenticationFilter {
    private ObjectMapper mapper=new ObjectMapper();
    private final TokenHandler tokenGenerator;
    private final AuthenticationManager authenticationManager;
    @Value("${spring.security.jwt.expiration}")
    private long expiryTime;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User requestUser = mapper.readValue(request.getInputStream(), User.class);
            Authentication authToken = new UsernamePasswordAuthenticationToken(requestUser.getUsername(), requestUser.getPassword());
            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User requestUser = mapper.readValue(request.getInputStream(), User.class);
        String jwt = tokenGenerator.generateToken(requestUser);
        ResponseToken token = new ResponseToken();
        token.setToken(jwt);
        token.setExpiresIn(tokenGenerator.getExpiryTime());

        response.addHeader("Header", String.format("Bearer %s", jwt));
        response.addHeader("Expiration", String.valueOf(expiryTime));

        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(token));
    }

}
