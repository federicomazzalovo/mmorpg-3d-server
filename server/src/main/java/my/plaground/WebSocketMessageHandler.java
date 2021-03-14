package my.plaground;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.plaground.Domain.Character;
import my.plaground.Domain.Position;
import my.plaground.Domain.WebSocketParams;
import my.plaground.Service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import static my.plaground.Domain.ActionType.*;

@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private final CharacterService characterService;

    private static Object monitor = new Object();

    @Autowired
    public WebSocketMessageHandler(CharacterService service){
        this.characterService = service;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // The WebSocket has been closed

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws InterruptedException, IOException {
        // The WebSocket has been opened
        // I might save this session object so that I can send messages to it outside of this method
        TimerTask timer  = new TimerTask() {
              @Override
              public void run() {
                  if(session.isOpen()) {
                      try {
                          String messageToSend = getCharacterPositionMessage();
                          session.sendMessage(new TextMessage(messageToSend));
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                  }else {
                    this.cancel();
                  }
              }
        };
        new Timer().scheduleAtFixedRate(timer, 0,100);
    }

    private String getCharacterPositionMessage() throws JsonProcessingException {
        List<Character> charactersAlive = this.characterService.getCharacters();
        List<WebSocketParams> webSocketResponse = charactersAlive.stream()
                .map(c -> new WebSocketParams(c.getId(), c.getPosition().getX(), c.getPosition().getY(), c.getMoveDirection(), c.getHp(), -1, None))
                .collect(Collectors.toList());

        return new ObjectMapper().writeValueAsString(webSocketResponse);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        // A message has been received
        System.out.println("Message received: " + textMessage.getPayload());

        try {
            WebSocketParams webSocketParams = new ObjectMapper().readValue(textMessage.getPayload(), WebSocketParams.class);

            switch(webSocketParams.getActionType())
            {
                case None:
                    break;
                case Movement:
                    this.characterService.updatePosition(webSocketParams.getCharacterId(), Position.at(webSocketParams.getPositionX(), webSocketParams.getPositionY()), webSocketParams.getMoveDirection());
                    break;
                case Attack:
                    this.characterService.attack(webSocketParams.getCharacterId(), webSocketParams.getTargetId());
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}