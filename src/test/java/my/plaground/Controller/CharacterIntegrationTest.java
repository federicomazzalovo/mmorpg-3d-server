package my.plaground.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import my.plaground.Character;
import my.plaground.Paladin;
import my.plaground.Service.CharacterService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static my.plaground.SimpleRpgKataApplication.getMockedCharacterList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Disabled
@WebMvcTest(controllers = CharacterController.class)
//@ActiveProfiles("test")
public class CharacterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private CharacterService service;

    @Test
    public void
    ensure_that_position_is_correct_for_specific_char() throws Exception {
        Character characterToFind = new Paladin();
        characterToFind.setId(1);
        //when(service.getCharacter(1)).thenReturn(Optional.of(characterToFind));

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/character/1/position")).andReturn();
        String jsonResult = result.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(characterToFind.getPosition()), jsonResult);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }



    @Test public void
    ensure_characters_list_is_valid_and_not_empty() throws Exception {

      ///  when(service.getCharacters()).thenReturn(getMockedCharacterList());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/character/all")).andReturn();
        String jsonResult = result.getResponse().getContentAsString();

        var resultList = this.objectMapper.readValue(jsonResult, List.class);

        assertTrue(resultList != null && !resultList.isEmpty());
    }

}
