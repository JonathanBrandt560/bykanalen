package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.JonathanAnton.bykanalen.model.EventRegistration;

import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {

    // Hitta alla registreringar för en specifik event
    List<EventRegistration> findByEventId(Long eventId);

    // Hitta alla events en user är registrerad på
    List<EventRegistration> findByUserId(Long userId);

    // Kontrollera om en user redan är registrerad på ett event
    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    // Hitta en specifik registrering
    Optional<EventRegistration> findByUserIdAndEventId(Long userId, Long eventId);

    // Få antalet registrerade deltagare för ett event
    long countByEventId(Long eventId);

    // Ta bort alla registreringar kopplade till ett event
    void deleteAllByEventId(Long eventId);
}
