package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.JonathanAnton.bykanalen.model.MemberlistGroup;

import java.util.List;
import java.util.Optional;

public interface MemberlistGroupRepository extends JpaRepository<MemberlistGroup, Long> {

    // Hitta alla grupper en user är medlem i
    List<MemberlistGroup> findByUserId(Long userId);

    // Hitta alla medlemmar i en grupp
    List<MemberlistGroup> findByGroupInfoId(Long groupId);

    // Kontrollera om en user är medlem i en grupp
    boolean existsByUserIdAndGroupInfoId(Long userId, Long groupId);

    // Hitta ett specifikt medlemskap
    Optional<MemberlistGroup> findByUserIdAndGroupInfoId(Long userId, Long groupId);

    // Få antalet medlemmar i en grupp
    long countByGroupInfoId(Long groupId);
}
