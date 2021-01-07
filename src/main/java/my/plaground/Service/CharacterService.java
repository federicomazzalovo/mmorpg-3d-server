package my.plaground.Service;

import my.plaground.Position;
import my.plaground.Repository.CharacterEntity;
import my.plaground.Repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import my.plaground.Character;

@Service
public class CharacterService {

    private final CharacterRepository repository;
    private final CharacterFactory characterFactory;

    public CharacterService(CharacterRepository repository, CharacterFactory characterFactory) {
        this.repository = repository;
        this.characterFactory = characterFactory;
    }

    public List<Character> getCharacters() {
        return this.repository.findAll().stream()
                    .map(this.characterFactory::getCharacter).collect(Collectors.toList());
    }



}
