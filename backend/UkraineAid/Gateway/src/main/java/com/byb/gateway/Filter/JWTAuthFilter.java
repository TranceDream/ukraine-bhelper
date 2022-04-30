package com.byb.gateway.Filter;

import com.alibaba.fastjson.JSONObject;
import com.byb.BaseUtil.Config.ConstantConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class JWTAuthFilter implements GlobalFilter , Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(ConstantConfig.TOKEN_HEADER);
        String url = exchange.getRequest().getURI().getPath();
        if(url.equals(ConstantConfig.LOGIN_PATH)){
            System.out.println("login");
            return chain.filter(exchange);
        }
        if(token == null){
            return unauthorized(exchange);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(ConstantConfig.TOKEN_HEADER, token);
        headers.add(ConstantConfig.REQUEST_HEADER, url);
        HttpEntity<String> formEntity = new HttpEntity<String>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        String isAuth =  restTemplate.postForObject(ConstantConfig.TOKEN_CHECK_URL, formEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(isAuth);
        if((int)jsonObject.get("code") == 200) {
            String userId = (String) jsonObject.get("data");
            ServerHttpRequest req = exchange.getRequest();
            HttpHeaders httpHeaders = req.getHeaders();
            ServerHttpRequest.Builder requestBuilder = req.mutate();
            requestBuilder.header(ConstantConfig.LOGIN_USER_HEADER, userId);
            ServerHttpRequest request = requestBuilder.build();
            exchange.mutate().request(request).build();
            return chain.filter(exchange);
        }
        else
            return unauthorized(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.UNAUTHORIZED.value());
        map.put("msg", "YOU DON'T HAVE THIS AUTHORITY!");
        map.put("data", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        String resp = JSONObject.toJSONString(map);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(resp.getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }
}
