package my.plaground.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import my.plaground.Character;
import my.plaground.Paladin;
import my.plaground.Position;
import my.plaground.Service.CharacterService;
import my.plaground.Wizard;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static my.plaground.Position.at;
import static my.plaground.SimpleRpgKataApplication.getMockedCharacterList;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
public class CharacterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private CharacterService service;


    @Test
    public void
    ensure_that_position_is_correct_for_specific_char() throws Exception {
        Character characterToFind = new Paladin();
        characterToFind.setPosition(at(8,2));
        characterToFind.setId(100);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/character/100/position")).andReturn();
        String jsonResult = result.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(characterToFind.getPosition()), jsonResult);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test
    public void
    ensure_throws_exception_if_character_id_is_invalid() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/character/-1/position")).andReturn();
        String jsonResult = result.getResponse().getContentAsString();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test public void
    ensure_that_position_is_correctly_updated() throws Exception {
        Position newPosition = at(9999,888);

        var request = put("/api/character/999/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(newPosition));
        MvcResult result = this.mockMvc.perform(request).andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(newPosition), jsonResult);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test public void
    ensure_characters_list_is_valid_and_not_empty() throws Exception {

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/character/all")).andReturn();
        String jsonResult = result.getResponse().getContentAsString();

        var resultList = this.objectMapper.readValue(jsonResult, List.class);

        assertTrue(resultList != null && !resultList.isEmpty());
    }

    @Test public void
    ensure_existing_character_is_found() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/character/100")).andReturn();
        String jsonResult = result.getResponse().getContentAsString();

        Paladin characterFound = this.objectMapper.readValue(jsonResult, Paladin.class);

        assertEquals(characterFound.getId(), 100);
    }

    @Disabled
    @Test public void
    ensure_that_character_in_range_can_attack_target() throws Exception {
        MvcResult attackRequest = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/character/1001/attack/1002")).andReturn();
      //  MvcResult characterOneRequest = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/character/1001")).andReturn();
        MvcResult CharacterTwoRequest = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/character/1002")).andReturn();

     //   Paladin paladin = this.objectMapper.readValue(characterOneRequest.getResponse().getContentAsString(), Paladin.class);
        Wizard wizard = this.objectMapper.readValue(CharacterTwoRequest.getResponse().getContentAsString(), Wizard.class);

        //assertEquals(paladin.getHp(), 150);
        assertTrue(wizard.getHp() <  120);
    }
}
