package my.plaground;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.plaground.Domain.Position;
import my.plaground.Domain.WebSocketParams;
import my.plaground.Service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private final CharacterService characterService;

    @Autowired
    public WebSocketMessageHandler(CharacterService service){
        this.characterService = service;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // The WebSocket has been closed
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // The WebSocket has been opened
        // I might save this session object so that I can send messages to it outside of this method

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        // A message has been received
        System.out.println("Message received: " + textMessage.getPayload());

        try {
            WebSocketParams webSocketParams = new ObjectMapper().readValue(textMessage.getPayload(), WebSocketParams.class);

            this.characterService.updatePosition(webSocketParams.getCharacterId(), Position.at(webSocketParams.getPositionX(), webSocketParams.getPositionY()));

            session.sendMessage(new TextMessage("Ue quaglio ho ricevuto questo messaggio da te: " + textMessage.getPayload()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}