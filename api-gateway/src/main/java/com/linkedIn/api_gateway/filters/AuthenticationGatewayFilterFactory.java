package com.linkedIn.api_gateway.filters;

import io.jsonwebtoken.JwtException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.linkedIn.api_gateway.service.JWTService;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(AuthenticationGatewayFilterFactory.class);

    private final JWTService jwtService;

    public AuthenticationGatewayFilterFactory(JWTService jwtService){
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Login request: {}", exchange.getRequest().getURI());

            final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(tokenHeader == null || !tokenHeader.startsWith("Bearer")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                log.error("Authorization token header not found");
                return exchange.getResponse().setComplete();
            }

            final String token = tokenHeader.split("Bearer ")[1];

            try{
                String userId = jwtService.getUserIdFromToken(token);
                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(r -> r.header("X-User-Id", userId))
                        .build();

                return chain.filter(modifiedExchange);
            }catch (JwtException e){
                log.error("JWT Exception: {}", e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }

    public static class Config{}
}
