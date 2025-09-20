package tw.dd.spring123.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import tw.dd.spring123.entity.Message;
import tw.dd.spring123.entity.User;
import tw.dd.spring123.repository.MessageRepository;
import tw.dd.spring123.repository.UserRepository;

import java.util.*;

@Component
public class GameSocketHandler extends TextWebSocketHandler {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private MessageRepository messageRepo;

	private final Set<WebSocketSession> sessions = new HashSet<>();
	private final Map<WebSocketSession, User> sessionUserMap = new HashMap<>();

	@Override       /*0920 1449*/
	public void afterConnectionEstablished(WebSocketSession session) {

		User user = (User) session.getAttributes().get("user");
		if (user == null) {
			throw new RuntimeException("尚未登入，不能進入 WebSocket");
		}

		sessionUserMap.put(session, user);
		sessions.add(session);
		broadcast("玩家加入：" + user.getUsername());
		System.out.println("玩家加入" + user.getUsername());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		String payload = message.getPayload();
		User user = sessionUserMap.get(session);
		Long roomId = 1L;
		messageRepo.save(new Message(user.getId(), session.getId(), roomId, payload));
		broadcast("玩家 " + user.getUsername() + " 說：" + payload);
		System.out.println("玩家" + user.getUsername() + " 說：" + payload);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

		User user = sessionUserMap.get(session);
		sessionUserMap.remove(session);
		sessions.remove(session);
		broadcast("玩家離開：" + user.getUsername());
		System.out.println("玩家離開" + user.getUsername());
	}

	private void broadcast(String msg) {
		for (WebSocketSession s : sessions) {
			try {
				s.sendMessage(new TextMessage(msg));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
