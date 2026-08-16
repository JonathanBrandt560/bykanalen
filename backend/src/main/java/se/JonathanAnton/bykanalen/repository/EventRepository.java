package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.JonathanAnton.bykanalen.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Hitta events som börjar efter ett visst datum
    List<Event> findByGroupInfoIdAndStartDateAfterOrderByStartDateDesc(Long groupId, LocalDateTime startDate);

    // Hitta events som börjar före ett visst datum
    List<Event> findByGroupInfoIdAndStartDateBeforeOrderByStartDateDesc(Long groupId, LocalDateTime startDate);

    // Hitta events efter publiceringsdatum
    List<Event> findByGroupInfoIdAndPublishDateAfterOrderByPublishDateDesc(Long groupId, LocalDateTime publishDate);

    // Hitta event utfrån grupp-id och evenemangs-id
    Optional<Event> findByGroupInfoIdAndId(Long groupId, Long id);

    // Custom query för att hitta events där registrering fortfarande är öppen
    @Query("SELECT e FROM Event e WHERE e.closeRegistrationDate IS NULL OR e.closeRegistrationDate > :now")
    List<Event> findOpenRegistrationEvents(@Param("now") LocalDateTime now);
}
