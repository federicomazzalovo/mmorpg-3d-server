package my.plaground.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.plaground.Character;
import my.plaground.Position;
import my.plaground.SimpleRpgKataApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PositionController.class)
class PositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test public void
    ensure_that_position_is_correct_for_specific_char() {
        try {
            Character characterToFind = SimpleRpgKataApplication.getCharacterList().stream().filter(c -> c.getId() == 1).findFirst().orElse(null);

            MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/position/1")).andReturn();
            String jsonResult = result.getResponse().getContentAsString();

            assertEquals(objectMapper.writeValueAsString(characterToFind.getPosition()), jsonResult);
            assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Disabled
    @Test public void
    ensure_that_position_is_correctly_updated() {
        try {
            Character characterToFind = SimpleRpgKataApplication.getCharacterList().stream().filter(c -> c.getId() == 1).findFirst().orElse(null);
            Position newPosition = Position.at(characterToFind.getPosition().getX()+2, characterToFind.getPosition().getY()+ 3);
            MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/api/position/1", newPosition)).andReturn();
            String jsonResult = result.getResponse().getContentAsString();

            assertEquals(objectMapper.writeValueAsString(newPosition), jsonResult);
            assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}