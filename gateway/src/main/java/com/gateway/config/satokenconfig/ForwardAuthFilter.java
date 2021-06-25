package com.gateway.config.satokenconfig;

import com.pj.auth.GatewayAuthUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器，添加网关鉴权token
 * @author kong
 *
 */
@Component
public class ForwardAuthFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println("----------------------------进入全局拦截器");
		// 创建一个新的Request 并添加上网关token
        ServerHttpRequest newRequest = exchange.getRequest().mutate()
        		.header(GatewayAuthUtil.REQUEST_TOKEN_KEY, GatewayAuthUtil.getToken()).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        return chain.filter(newExchange);
	}


}