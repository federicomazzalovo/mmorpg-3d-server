package my.plaground.Controller;


import lombok.extern.slf4j.Slf4j;
import my.plaground.Character;
import my.plaground.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static my.plaground.SimpleRpgKataApplication.getMockedCharacterList;

@Slf4j
@RestController
@RequestMapping("/api/character")
public class CharacterController {

    @GetMapping("/{characterId}/position")
    public ResponseEntity<Position> getCharacterPosition(@PathVariable Integer characterId) throws Exception {
        Character character = getMockedCharacterList().stream()
                .filter(c -> c.getId() == characterId)
                .findFirst()
                .orElseThrow(() -> new Exception("Employee not found for this id :: " + characterId));

        return ResponseEntity.ok(character.getPosition());

    }


    @PutMapping("/{characterId}/position")
    public ResponseEntity<Position> updateCharacterPosition(@PathVariable Integer characterId, @RequestBody Position newPosition) throws Exception {
        Character character = getMockedCharacterList().stream()
                .filter(c -> c.getId() == characterId)
                .findFirst()
                .orElseThrow(() -> new Exception("Employee not found for this id :: " + characterId));

        character.setPosition(newPosition);
        return ResponseEntity.ok(newPosition);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Character>> getCharacterList() throws Exception {
        return ResponseEntity.ok(getMockedCharacterList());
    }
}