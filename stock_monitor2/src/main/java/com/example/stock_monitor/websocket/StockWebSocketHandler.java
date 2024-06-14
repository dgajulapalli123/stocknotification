package com.example.stock_monitor.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class StockWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle WebSocket messages
    }

    public void sendMessageToClient(WebSocketSession session, String message) throws Exception {
        session.sendMessage(new TextMessage(message));
    }
}
