package my.plaground.Controller;


import lombok.extern.slf4j.Slf4j;
import my.plaground.Domain.DTO.LoginRequest;
import my.plaground.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) throws Exception {
        boolean result =  userService.login(request.getUsername(), request.getCharacterClass());

        if(result)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().build();
    }
}
