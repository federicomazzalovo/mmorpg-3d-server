package my.plaground.Controller;


import lombok.extern.slf4j.Slf4j;
import my.plaground.Position;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/position")
public class PositionController {

    @GetMapping("/{playerId}")
    public Position getPlayerPosition(String playerId) {
        return new Position(0,0);
    }
}
