package my.plaground.Service;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import my.plaground.Domain.Character;
import my.plaground.Domain.CharacterClass;
import my.plaground.Domain.Entity.UserEntity;
import my.plaground.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
public class UserService {
    private final UserRepository repository;
    private final CharacterService characterService;

    public UserService(UserRepository repository, CharacterService characterService) {
        this.repository = repository;
        this.characterService = characterService;
    }



    public boolean login(String username, CharacterClass characterClass) {
        Optional<Character> optionalCharacter = getCharacterByUsernameAndClassId(username, characterClass);
        if(optionalCharacter.isEmpty()){
            Character character = createCharacterByUsernameAndClassId();
            return character != null;
        }

        return true;
    }


    public Optional<Character> getCharacterByUsernameAndClassId(String username, CharacterClass characterClass){
        UserEntity user = this.repository.findByUsername(username);
        List<Character> characters = this.characterService.getCharactersByUser(user.getId());

        List<Character> selectedClassChars = characters.stream().filter(character1 -> character1.getCharacterClass() == characterClass).collect(Collectors.toList());
        if(selectedClassChars.stream().count() > 1)
            log.warn("Multiple characters found for user: " + username + " class: " + characterClass.toString());

        return selectedClassChars.stream().findFirst();
    }

    private Character createCharacterByUsernameAndClassId() {
        return null;
    }

}
