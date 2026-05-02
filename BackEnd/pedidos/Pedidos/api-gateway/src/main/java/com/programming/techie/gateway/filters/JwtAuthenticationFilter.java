package com.programming.techie.gateway.filters;

import com.programming.techie.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // 🔓 rutas públicas
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = JwtUtil.validateToken(token);

            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);

            logger.info("Usuario: {}", username);
            logger.info("Roles: {}", roles);

            // 🔥 PROPAGAR A MICROSERVICIOS
            return chain.filter(
                    exchange.mutate()
                            .request(builder -> builder
                                    .header("X-User", username)
                                    .header("X-Roles", String.join(",", roles))
                            )
                            .build()
            );

        } catch (Exception e) {
            logger.error("Token inválido: {}", e.getMessage());
            return unauthorized(exchange);
        }
    }

    private boolean isValid(String token) {
        try {
            JwtUtil.validateToken(token);
            logger.info("TOKEN VALIDO");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.info("TOKEN INVALIDO: " + e.getMessage());
            return false;
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
