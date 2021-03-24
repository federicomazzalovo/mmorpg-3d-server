package my.plaground.Service;

import my.plaground.Domain.Character;
import my.plaground.Domain.CharacterClass;
import my.plaground.Domain.Entity.CharacterEntity;
import my.plaground.Domain.Rogue;
import my.plaground.Repository.CharacterRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles({"test"})
public class CharacterServiceTest {

    @InjectMocks
    private CharacterService service;

    @Mock
    private CharacterRepository repository;

    @Spy
    private CharacterFactory factory;

    @BeforeEach
    public void setup(){
        CharacterEntity c1 = CharacterEntity.builder().id(1).hp(0).isConnected(false).levelValue(1).classId(CharacterClass.Paladin).build();
        CharacterEntity c2 = CharacterEntity.builder().id(2).hp(100).isConnected(true).levelValue(1).classId(CharacterClass.Wizard).build();
        CharacterEntity c3 = CharacterEntity.builder().id(3).hp(150).isConnected(true).levelValue(1).classId(CharacterClass.Rogue).build();
        when(repository.findAll()).thenReturn(List.of(c1,c2,c3));
        when(repository.findById(3)).thenReturn(Optional.of(c3));
    }

    @Test public void
    ensure_getCharacters_does_not_return_null() {
        List<Character> characters = this.service.getCharacters();
        assertNotNull(characters);
    }


    @Test public void
    ensure_that_get_alive_characters_returns_only_alive_characters() {
        List<Character> aliveCharacters = this.service.getCharactersAlive();

       assertNotNull(aliveCharacters);
       assertTrue(aliveCharacters.size() > 0 && aliveCharacters.stream().allMatch(Character::isAlive) );
    }


    @Test public void
    ensure_that_get_connected_characters_returns_only_connected_characters() {
        List<Character> connectedCharacters = this.service.getCharactersConnected();

        assertNotNull(connectedCharacters);
        assertTrue(connectedCharacters.size() > 0 && connectedCharacters.stream().allMatch(Character::isConnected) );
    }

    @Test public void
    ensure_get_character_of_existing_character_returns_correct_data() {
        Optional<Character> characterOptional = this.service.getCharacter(3);

        assertTrue(characterOptional.isPresent());

        Character character = characterOptional.get();

        assertEquals(3, character.getId());
        assertEquals(150, character.getHp());
        assertEquals(1, character.getLevel());
        assertEquals(CharacterClass.Rogue, character.getCharacterClass());
        assertTrue(character.isConnected());
    }
}