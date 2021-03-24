package my.plaground.Service;

import my.plaground.Domain.Character;
import my.plaground.Domain.Entity.CharacterEntity;
import my.plaground.Repository.CharacterRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles({"test"})
public class CharacterServiceTest {

    @InjectMocks
    private CharacterService service;

    @Mock
    private CharacterRepository repository;

    @Mock
    private CharacterFactory factory;

    @BeforeEach
    public void setup(){
        when(repository.findAll()).thenReturn(List.of(new CharacterEntity()));
    }

    @Test public void
    ensure_getCharacters_does_not_return_null(){
        List<Character> characters = this.service.getCharacters();
        assertNotNull(characters);
    }
}