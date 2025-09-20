package tw.dd.spring123.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import tw.dd.spring123.websocket.GameSocketHandler;
import tw.dd.spring123.websocket.HttpSessionInterceptor;

@Configuration
@EnableWebSocket
public class WebSockletConfig implements WebSocketConfigurer {

	@Autowired
	private GameSocketHandler gameSocketHandler;
	
	@Autowired
    private HttpSessionInterceptor httpSessionInterceptor;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(gameSocketHandler, "/game")
				.addInterceptors(httpSessionInterceptor)
				.setAllowedOrigins("*");
	}
}
