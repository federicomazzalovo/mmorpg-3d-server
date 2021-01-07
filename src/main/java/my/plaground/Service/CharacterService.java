package my.plaground.Service;

import my.plaground.Repository.CharacterEntity;
import my.plaground.Repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }


    public List<Character> getCharacters() {
        List<CharacterEntity> entities = this.repository.findAll();

        return null;
    }


}
