package com.peppermint.poc.gateway.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.peppermint.poc.gateway.filters.AuthenticationPrefilter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class DefaultConfig {

    //reading whitelisted urls from properties
    @Value("${spring.gateway.whitelistedUrls}")
    private String urlsStrings;

    //storing whitelisted urls in a list
    @Bean
    @Qualifier("whitelistedUrls")
    public List<String> whitelistedUrls() {
        return Arrays.stream(urlsStrings.split(",")).collect(Collectors.toList());
    }


    //used when sending error response as json
    @Bean
    public ObjectMapper objectMapper() {
        JsonFactory factory = new JsonFactory();
        factory.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

        ObjectMapper objectMapper = new ObjectMapper(factory);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return objectMapper;
    }


    //this will check path, validate and add token to request and redirect it
    @Bean
    public RouteLocator routes(
            RouteLocatorBuilder builder,
            AuthenticationPrefilter authFilter) {
        return builder.routes()
                .route("user_management-route", r -> r.path("/user_management/**")
                        .filters(f ->
                                f.rewritePath("/user_management(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(
                                                new AuthenticationPrefilter.Config())))
                        .uri("lb://user_management"))
                .route("dashboard-route", r -> r.path("/dashboard/**")
                        .filters(f ->
                                f.rewritePath("/dashboard(?<segment>/?.*)", "$\\{segment}")
                                        .filter(authFilter.apply(
                                                new AuthenticationPrefilter.Config())))
                        .uri("lb://dashboard"))
                .build();
    }
}
