package my.plaground.Service;

import my.plaground.Exception.ResourceNotFound;
import my.plaground.Domain.Position;
import my.plaground.Repository.CharacterEntity;
import my.plaground.Repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import my.plaground.Domain.Character;

import javax.persistence.EntityNotFoundException;

import static javax.swing.text.html.HTML.Tag.S;

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

    public List<Character> getCharactersAlive() {
        return  this.getCharacters().stream()
                .filter(Character::isAlive).collect(Collectors.toList());
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

    public Character updateCharacter(Character updatedCharacter) {
        CharacterEntity character = this.repository.getOne(updatedCharacter.getId());
        character.setPositionx(updatedCharacter.getPosition().getX());
        character.setPositiony(updatedCharacter.getPosition().getY());
        character.setHp(updatedCharacter.getHp());
        character.setLevelValue(updatedCharacter.getLevel());
        CharacterEntity charSaved = this.repository.save(character);
        return  this.characterFactory.getCharacter(charSaved);
    }

    public void attack(int characterId, int targetId) {
        Optional<Character> attackerFromDB = this.getCharacter(characterId);
        Optional<Character> targetFromDB = this.getCharacter(targetId);

        if(attackerFromDB.isEmpty() || targetFromDB.isEmpty())
            throw new ResourceNotFound();

        Character attacker = attackerFromDB.get();
        Character target = targetFromDB.get();

        attacker.attack(target);

        this.updateCharacter(target);
    }


}
