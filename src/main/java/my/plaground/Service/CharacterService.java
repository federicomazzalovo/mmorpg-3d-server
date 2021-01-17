package my.plaground.Service;

import my.plaground.Position;
import my.plaground.Repository.CharacterEntity;
import my.plaground.Repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import my.plaground.Character;

import javax.persistence.EntityNotFoundException;

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

    public Optional<Character> getCharacter(int characterId){
        try {
            return Optional.of(this.characterFactory.getCharacter(this.repository.getOne(characterId)));
        } catch (EntityNotFoundException e){
            return Optional.empty();
        }
    }


    public Character updatePosition(int characterId, Position newPosition) {
        CharacterEntity character = this.repository.getOne(characterId);
        character.setPositionx(newPosition.getX());
        character.setPositiony(newPosition.getY());
        CharacterEntity charSaved = this.repository.save(character);
        return  this.characterFactory.getCharacter(charSaved);
    }

}
