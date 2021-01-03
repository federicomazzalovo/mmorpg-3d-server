package my.plaground.Controller;


import lombok.extern.slf4j.Slf4j;
import my.plaground.Character;
import my.plaground.Position;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static my.plaground.SimpleRpgKataApplication.getCharacterList;

@Slf4j
@RestController
@RequestMapping("/api/position")
public class PositionController {

    @GetMapping("/{characterId}")
    public ResponseEntity<Position> getCharacterPosition(@PathVariable Integer characterId) throws Exception {
        Character character = getCharacterList().stream()
                .filter(c -> c.getId() == characterId)
                .findFirst()
                .orElseThrow(() -> new Exception("Employee not found for this id :: " + characterId));

        return ResponseEntity.ok(character.getPosition());

    }


    @PutMapping("/{characterId}")
    public ResponseEntity<Position> updateCharacterPosition(@PathVariable Integer characterId, @RequestBody Position newPosition) throws Exception {
        Character character = getCharacterList().stream()
                .filter(c -> c.getId() == characterId)
                .findFirst()
                .orElseThrow(() -> new Exception("Employee not found for this id :: " + characterId));

        character.setPosition(newPosition);
        return ResponseEntity.ok(newPosition);

    }
}
