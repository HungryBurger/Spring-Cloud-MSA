package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //Custom Pre Filter
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE filter: request id -> {}", request.getId());

            //Custom Post Filter
            //Mono라는 webflux모듈에  spring5에서 사용된다 기존의 동기 방식이 아닌 비동기 방식의 서버를 지원할때 단위값 지원할때 스는 단위이다.
            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
                log.info("Custom POST filter: response code ->{}",response.getStatusCode());
            }));
        });
    }

    public static class Config {
        //Put the configuration prorperties

    }
}
