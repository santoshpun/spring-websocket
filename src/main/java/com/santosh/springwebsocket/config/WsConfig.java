package com.santosh.springwebsocket.config;

import com.santosh.springwebsocket.handler.WebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Slf4j
@Configuration
@EnableWebSocket
public class WsConfig implements WebSocketConfigurer {

    public static final String ENDPOINT_CONNECT = "/socket";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(), ENDPOINT_CONNECT)
                .setAllowedOrigins("*");
    }
}
