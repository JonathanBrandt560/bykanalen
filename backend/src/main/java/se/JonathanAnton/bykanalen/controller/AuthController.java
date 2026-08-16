package se.JonathanAnton.bykanalen.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.JonathanAnton.bykanalen.dto.LoginDTO;
import se.JonathanAnton.bykanalen.dto.RegisterDTO;
import se.JonathanAnton.bykanalen.service.UserService;

/**
 * REST-controller för att hantera HTTP-förfrågningar kopplade till registrering/inloggning.
 * Alla anrop till denna controller startar med bas-URL:en /auth
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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
}
