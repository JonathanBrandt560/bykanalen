package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupInfoRepository extends JpaRepository<GroupInfo, Long> {

    // Hitta group by namn
    Optional<GroupInfo> findByGroupName(String groupName);

    // Hitta groups efter namn (case-insensitive)
    List<GroupInfo> findByGroupNameContainingIgnoreCase(String groupName);

    // Hitta alla groups sorterade efter senaste
    List<GroupInfo> findAllByOrderByCreatedDateDesc();

}
