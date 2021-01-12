package my.plaground.Controller;


import lombok.extern.slf4j.Slf4j;
import my.plaground.Character;
import my.plaground.Position;
import my.plaground.Service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static my.plaground.SimpleRpgKataApplication.getMockedCharacterList;


@Slf4j
@RestController
@RequestMapping("/api/character")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/{characterId}/position")
    public ResponseEntity<Position> getCharacterPosition(@PathVariable Integer characterId) throws Exception {
        Character character = this.characterService.getCharacter(characterId)
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
        return ResponseEntity.ok(this.characterService.getCharacters());
    }
}