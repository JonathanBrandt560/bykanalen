package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.JonathanAnton.bykanalen.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    // Hitta group by namn
    Optional<Group> findByGroupName(String groupName);

    // Hitta groups efter namn (case-insensitive)
    List<Group> findByGroupNameContainingIgnoreCase(String groupName);

    // Hitta alla groups sorterade efter senaste
    List<Group> findAllByOrderByCreatedDateDesc();

}
