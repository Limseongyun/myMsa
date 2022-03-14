package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;
@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfigDefault implements WebSocketConfigurer{
	private final ChatHandler chatHandler;
	private final RoomChatHandler roomChatHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
		
		registry.addHandler(roomChatHandler, "room/*").setAllowedOrigins("*");
	}

}