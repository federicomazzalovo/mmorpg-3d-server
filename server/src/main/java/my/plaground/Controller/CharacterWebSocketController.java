package my.plaground.Controller;


import my.plaground.Domain.Character;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
public class CharacterWebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    @CrossOrigin(origins = "http://localhost:8080")
    public String greeting(String message) throws Exception {
       // Thread.sleep(1000); // simulated delay
       return "Receive: " + message + "Server websocket works";
    }
}
