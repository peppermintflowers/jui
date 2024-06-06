package com.peppermint.poc.gateway.filters;


import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peppermint.poc.gateway.model.ResponseToken;
import com.peppermint.poc.gateway.model.ExceptionResponseModel;

import reactor.core.publisher.Mono;
@Slf4j
@Component
public class AuthenticationPrefilter extends AbstractGatewayFilterFactory<AuthenticationPrefilter.Config> {
    @Autowired
    @Qualifier("whitelistedUrls")
    List<String> whitelistedUrls;
    private final WebClient.Builder webClientBuilder;
    private ObjectMapper objectMapper;
    public AuthenticationPrefilter(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        super(Config.class);
        this.webClientBuilder=webClientBuilder;
        this.objectMapper = objectMapper;
    }

    public static class Config {


    }
    //hits validateToken and sets into request, else returns error
    @Override
    public GatewayFilter apply(Config config) {
        return(exchange, chain)-> {
            log.info("In AuthenticationPrefilter");
            ServerHttpRequest request = exchange.getRequest();
            String bearerToken = request.getHeaders().getFirst("Authorization");
             if(isSecured.test(request)) {
                 return webClientBuilder.build().get()
                         .uri("http://localhost:9004/api/v1/validateToken")
                         .header("Authorization", bearerToken)
                         .retrieve().bodyToMono(ResponseToken.class)
                         .map(response -> {
                             exchange.getRequest().mutate().header("username", response.getUsername());
                             exchange.getRequest().mutate().header("token", response.getToken());
                             return exchange;
                         }).flatMap(chain::filter).onErrorResume(error -> {
                             HttpStatus errorCode = null;
                             String errorMsg = "";
                             if (error instanceof WebClientResponseException) {
                                 WebClientResponseException webCLientException = (WebClientResponseException) error;
                                 errorCode = (HttpStatus) webCLientException.getStatusCode();
                                 errorMsg = webCLientException.getStatusText();

                             } else {
                                 errorCode = HttpStatus.BAD_GATEWAY;
                                 errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
                             }
//                            AuthorizationFilter.AUTH_FAILED_CODE
                             return onError(exchange, String.valueOf(errorCode.value()), errorMsg, "JWT Authentication Failed", errorCode);
                         });
             }
                 return chain.filter(exchange);
             };
    }
    public Predicate<ServerHttpRequest> isSecured = request -> whitelistedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
    private Mono<Void> onError(ServerWebExchange exchange, String errCode, String err, String errDetails, HttpStatus httpStatus) {
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        try {
            response.getHeaders().add("Content-Type", "application/json");
            ExceptionResponseModel data = new ExceptionResponseModel(errCode, err, errDetails, new Date());
            byte[] byteData = objectMapper.writeValueAsBytes(data);
            return response.writeWith(Mono.just(byteData).map(t -> dataBufferFactory.wrap(t)));

        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }
        return response.setComplete();
    }

}