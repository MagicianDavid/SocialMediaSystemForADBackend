package com.example.demo.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketUserHandler extends TextWebSocketHandler {

    // WebSocketSession to save user
    private final Map<Integer, WebSocketSession> userSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer userId = getUserIdFromSession(session);
        userSessions.put(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer userId = getUserIdFromSession(session);
        userSessions.remove(userId);
    }

    public void sendUserUpdate(Integer userId) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage("Current User has been updated"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendAllUserUpdate() {
        for (WebSocketSession session : userSessions.values()) {
            if (session != null && session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage("All User has been updated"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Integer getUserIdFromSession(WebSocketSession session) {
        return Integer.valueOf(session.getUri().getQuery().split("=")[1]);
    }
}