package se.JonathanAnton.bykanalen.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.dto.LoginDTO;
import se.JonathanAnton.bykanalen.dto.RegisterDTO;
import se.JonathanAnton.bykanalen.dto.UserDetailDTO;
import se.JonathanAnton.bykanalen.mapper.UserMapper;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.service.AuthorizationService;
import se.JonathanAnton.bykanalen.service.UserService;

/**
 * REST-controller för att hantera HTTP-förfrågningar kopplade till registrering/inloggning.
 * Alla anrop till denna controller startar med bas-URL:en /auth
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final UserMapper userMapper;

    public AuthController(UserService userService, AuthorizationService authorizationService, UserMapper userMapper) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.userMapper = userMapper;
    }

    // Endpoint för registrering av ny användare
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO dto) {
        userService.register(dto);
        return ResponseEntity.status(201).body("Användare skapad");
    }

    // Endpoint för inloggning
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        String token = userService.login(dto);
        return ResponseEntity.ok(token);
    }

    // Hämtar info (roll, spärrstatus m.m.) om den inloggade användaren
    @GetMapping("/me")
    public ResponseEntity<UserDetailDTO> getCurrentUser() {
        User user = authorizationService.getCurrentUser();
        return ResponseEntity.ok(userMapper.toUserDetailDTO(user));
    }
}
