package my.plaground.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.plaground.Character;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PositionController.class)
class PositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Disabled
    @Test public void
    ensure_that_position_is_correct_for_specific_char() {
        try {
            Character characterToFind = SimpleRpgKataApplication.getCharacterList().stream().filter(c -> c.getId() == 1).findFirst().orElse(null);

            MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/position/1")).andReturn();
            String jsonResult = result.getResponse().getContentAsString();

            assertEquals(objectMapper.writeValueAsString(characterToFind.getPosition()), jsonResult);
            assertEquals(HttpStatus.OK, result.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}