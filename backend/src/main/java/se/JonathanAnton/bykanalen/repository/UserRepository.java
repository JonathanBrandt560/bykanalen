package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.JonathanAnton.bykanalen.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Sök efter användare baserat på användarnamn
    Optional<User> findByUsername(String username);

    // Undersöker om användare redan är registrerad baserat på användarnamn
    boolean existsByUsername(String username);

    //Sök efter användare baserat på email-address
    Optional<User> findByEmail(String email);

    // Undersöker om användare redan är registrerad baserat på email-address
    boolean existsByEmail(String email);
}
