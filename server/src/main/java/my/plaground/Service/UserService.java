package my.plaground.Service;

import my.plaground.Domain.Character;
import my.plaground.Exception.ResourceNotFound;
import my.plaground.Domain.Entity.UserEntity;
import my.plaground.Repository.CharacterRepository;
import my.plaground.Repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }



    public boolean login(String username, int classId) {
        Optional<Character> optionalCharacter = getCharacterByUsernameAndClassId(username, classId);
        if(optionalCharacter.isEmpty()){
            Character character = createCharacterByUsernameAndClassId();
            return character != null;
        }

        return true;
    }


    public Optional<Character> getCharacterByUsernameAndClassId(String username, int classId){
        return null;
    }

    private Character createCharacterByUsernameAndClassId() {
        return null;
    }

}
