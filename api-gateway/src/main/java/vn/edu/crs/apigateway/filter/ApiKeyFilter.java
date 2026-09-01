package vn.edu.crs.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyFilter implements GlobalFilter, Ordered {

    @Value("${partner.api-key}")
    private String coursesApiKey;

    @Value("${partner.student-api-key}")
    private String studentsApiKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        String requiredKey;
        if (path.startsWith("/api/public/courses")) {
            requiredKey = coursesApiKey;
        } else if (path.startsWith("/api/public/students")) {
            requiredKey = studentsApiKey;
        } else {
            return chain.filter(exchange);
        }

        String apiKey = request.getHeaders().getFirst("X-API-KEY");
        if (apiKey == null || !apiKey.equals(requiredKey)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}