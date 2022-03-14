package com.example.demo.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RoomChatHandler extends TextWebSocketHandler{

	private static Map<String, List<WebSocketSession>> room = new HashMap<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("added client: {}", session);
		String sessUri = session.getUri().getPath();
		Integer indexOfRoom = sessUri.indexOf("room/");
		String roomName = sessUri.substring(indexOfRoom + 5);
		List<WebSocketSession> roomInners = room.get(roomName);
		if(roomInners != null) {
			log.debug("room exists!");
			roomInners.add(session);
			room.put(roomName, roomInners);
			TextMessage tm = new TextMessage(session.getId() + " is join");
			for(WebSocketSession e: roomInners) {
				e.sendMessage(tm);
			}
		} else {
			log.debug("new Room created!");
			List<WebSocketSession> newRoomInners = new ArrayList<>();
			newRoomInners.add(session);
			room.put(roomName, newRoomInners);
		}
		
		log.debug("now room is {}", room);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("remove client: {}", session);
		String sessUri = session.getUri().getPath();
		Integer indexOfRoom = sessUri.indexOf("room/");
		String roomName = sessUri.substring(indexOfRoom + 5);
		List<WebSocketSession> roomInners = room.get(roomName);
		if(roomInners != null) {
			roomInners.remove(session);
			room.put(roomName, roomInners);
			TextMessage tm = new TextMessage(session.getId() + " is out");
			for(WebSocketSession e: roomInners) {
				e.sendMessage(tm);
			}
		} else {
			log.debug("??? disconnect room is not exists ");
		}
		
		log.debug("now room is {}", room);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String sessUri = session.getUri().getPath();
		Integer indexOfRoom = sessUri.indexOf("room/");
		String roomName = sessUri.substring(indexOfRoom + 5);
		List<WebSocketSession> roomInners = room.get(roomName);
		
		for(WebSocketSession e: roomInners) {
			e.sendMessage(message);
		}
	}
	
}
