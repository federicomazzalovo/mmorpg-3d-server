package my.plaground.Controller;


import lombok.extern.slf4j.Slf4j;
import my.plaground.Domain.Character;
import my.plaground.Domain.DTO.LoginRequest;
import my.plaground.Exception.ResourceNotFound;
import my.plaground.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
        boolean result =  userService.login(request.getUsername(), request.getClassId());

        return ResponseEntity.ok().build();
    }
}
