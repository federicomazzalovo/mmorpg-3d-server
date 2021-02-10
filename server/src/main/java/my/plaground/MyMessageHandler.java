package my.plaground;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class MyMessageHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // The WebSocket has been closed
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // The WebSocket has been opened
        // I might save this session object so that I can send messages to it outside of this method

        // Let's send the first message
        try {
            session.sendMessage(new TextMessage("You are now connected to the server. This is the first message."));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        // A message has been received
        System.out.println("Message received: " + textMessage.getPayload());

        try {
            session.sendMessage(new TextMessage("Ue quaglio ho ricevuto questo messaggio da te: " + textMessage.getPayload()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}