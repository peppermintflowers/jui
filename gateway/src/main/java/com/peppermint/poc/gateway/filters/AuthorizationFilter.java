package com.peppermint.poc.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peppermint.poc.gateway.model.ExceptionResponseModel;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class AuthorizationFilter implements GlobalFilter {

    @Autowired
    @Qualifier("whitelistedUrls")
    List<String> whitelistedUrls;

    @Value("${jwt.secret}")
    private String jwtSecret;

    //pass request ahead in filter chain if whitelisted or has access
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("In AuthorizationFilter");
        ServerHttpRequest req = exchange.getRequest();
        if(isSecured.test(req)) {
            try {
                boolean hasAccess = authenticate(req);
                if(!hasAccess) {
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }
            } catch (ExpiredJwtException e) {
                return this.onError(exchange, "Authorization header has expired", HttpStatus.UNAUTHORIZED);
            } catch (JwtException e) {
                return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
            }
        }
        log.info("Don't worry this request is whitelisted.");
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        ObjectMapper objMapper = new ObjectMapper();
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        try {
            response.getHeaders().add("Content-Type", "application/json");
            ExceptionResponseModel data = new ExceptionResponseModel("401", "JWT Error", err, new Date());
            byte[] byteData = objMapper.writeValueAsBytes(data);
            return response.writeWith(Mono.just(byteData).map(t -> dataBufferFactory.wrap(t)));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response.setComplete();
    }

    //check if request is whitelisted
    public Predicate<ServerHttpRequest> isSecured = request -> whitelistedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));

//authenticates jwt token extracted from request
    private boolean authenticate(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst("Authorization");
        if (token != null) {
            Claims user = Jwts.parser().setSigningKey(jwtSecret).build().parseSignedClaims(token).getPayload();

            if (user != null) {
                return true;
            }else{
                return  false;
            }

        }
        return false;
    }
}
