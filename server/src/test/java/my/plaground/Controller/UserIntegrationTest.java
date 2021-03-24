package my.plaground.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.plaground.Domain.CharacterClass;
import my.plaground.Domain.DTO.LoginRequest;
import my.plaground.Service.CharacterService;
import my.plaground.Service.UserService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private UserService service;

    @Test
    public void ensure_that_login_succeed_with_existing_username_and_class_id() throws Exception {
        LoginRequest body = new LoginRequest("PippoFranco", CharacterClass.Wizard);
        var request = post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(body));

        MvcResult result = this.mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }

    @Test
    public void ensure_that_login_succeed_with_existing_username_but_not_class_id() throws Exception {
        LoginRequest body = new LoginRequest("PippoFranco", CharacterClass.Rogue);
        var request = post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(body));

        MvcResult result = this.mockMvc.perform(request).andReturn();

        assertEquals( HttpStatus.OK.value(),result.getResponse().getStatus() );

    }


    @Test
    public void ensure_that_login_fail_with_not_existing_username() throws Exception {
        LoginRequest body = new LoginRequest("NOT_EXIST", CharacterClass.Wizard);
        var request = post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(body));

        MvcResult result = this.mockMvc.perform(request).andReturn();

        assertEquals( HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

    }

}