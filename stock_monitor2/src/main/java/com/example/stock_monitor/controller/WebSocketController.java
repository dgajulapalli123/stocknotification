package com.example.stock_monitor.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/stock")
    @SendTo("/topic/stocks")
    public String sendStockUpdates(String message) {
        return message;
    }
}
