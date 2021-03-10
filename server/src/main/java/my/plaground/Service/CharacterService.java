package my.plaground.Service;

import my.plaground.Domain.Character;
import my.plaground.Domain.MoveDirection;
import my.plaground.Domain.Position;
import my.plaground.Exception.ResourceNotFound;
import my.plaground.Domain.Entity.CharacterEntity;
import my.plaground.Repository.CharacterRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Character> getCharactersConnected() {
        return  this.getCharacters().stream()
                .filter(Character::isConnected).collect(Collectors.toList());
    }

    public Optional<Character> getCharacter(int characterId){
        try {
            Optional<CharacterEntity> character = this.repository.findById(characterId);
            return character.map(this.characterFactory::getCharacter);
        } catch (EntityNotFoundException e){
            return Optional.empty();
        }
    }

    public Character updatePosition(int characterId, Position newPosition, MoveDirection moveDirection) {
        Optional<CharacterEntity> character = this.repository.findById(characterId);
        if(character.isPresent()) {
            character.get().setPositionx(newPosition.getX());
            character.get().setPositiony(newPosition.getY());
            character.get().setMoveDirection(moveDirection);
            CharacterEntity charSaved = this.repository.save(character.get());

            return this.characterFactory.getCharacter(charSaved);
        }
        else
            return null;
    }

    public Character updateCharacter(Character updatedCharacter) {
        Optional<CharacterEntity> characterFromDb = this.repository.findById(updatedCharacter.getId());

        if(characterFromDb.isPresent()) {
            CharacterEntity character = characterFromDb.get();

            character.setPositionx(updatedCharacter.getPosition().getX());
            character.setPositiony(updatedCharacter.getPosition().getY());
            character.setHp(updatedCharacter.getHp());
            character.setLevelValue(updatedCharacter.getLevel());
            CharacterEntity charSaved = this.repository.save(character);

            return this.characterFactory.getCharacter(charSaved);
        }

        return null;
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

    public List<Character> getCharactersByUser(int userId) {
        List<CharacterEntity> characters = this.repository.findByUserId(userId);
        return characters.stream().map(this.characterFactory::getCharacter).collect(Collectors.toList());
    }
}
