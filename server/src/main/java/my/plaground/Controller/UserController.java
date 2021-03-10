package my.plaground.Controller;


import lombok.extern.slf4j.Slf4j;
import my.plaground.Domain.Character;
import my.plaground.Exception.ResourceNotFound;
import my.plaground.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}/character/{classId}")
    public ResponseEntity<Character> getUserCharacterByClassId(@PathVariable String username, @PathVariable int classId) throws Exception {
        return null;
    }
}
