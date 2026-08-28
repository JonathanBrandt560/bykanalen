package se.JonathanAnton.bykanalen.service;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.UserRepository;

/** Service-klass som implementerar Spring Security:s UserDetailsService-Gränssnitt.
 * Anropas automatiskt av Spring Security internt - dels av AuthenticationManager
 * vid inloggning (UserService.login), dels av JwtAuthFilter vid varje autentiserad
 * förfrågan för att slå upp den inloggade användarens roll och behörigheter. */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* Tar emot ett användarnnamn och returnerar ett Spring Security-kompatibelt UserDetails-objekt,
    byggt utifrån projektets egen User-entitet */
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException  {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String role = user.getUserDetail() != null ? user.getUserDetail().getType().name().toUpperCase() : "USER";
        /* Fullständigt klassnamn används för att undvika namnkollidering med projektets egen
        User-entitet (se.JonathanAnton.bykanalen.model.User) */
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(role)
                .build();
    }
}
