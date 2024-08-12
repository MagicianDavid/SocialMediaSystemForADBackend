package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketNotificationHandler webSocketNotificationHandler;
    private final WebSocketReportHandler webSocketReportHandler;

    public WebSocketConfig(WebSocketNotificationHandler webSocketNotificationHandler,
                           WebSocketReportHandler webSocketReportHandler) {
        this.webSocketNotificationHandler = webSocketNotificationHandler;
        this.webSocketReportHandler = webSocketReportHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketNotificationHandler, "/notifications")
                .setAllowedOrigins("http://localhost:3000");
        registry.addHandler(webSocketReportHandler, "/reports")
                .setAllowedOrigins("http://localhost:3000");
    }
}

