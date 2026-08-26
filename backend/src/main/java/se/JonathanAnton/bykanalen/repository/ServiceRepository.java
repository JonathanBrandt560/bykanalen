package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.JonathanAnton.bykanalen.model.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    // Hitta alla tjänster kopplade till en grupp/by
    List<Service> findByGroupInfoId(Long groupId);

    List<Service> findAllByOrderByPublishDateDesc();

    List<Service> findByTitleContainingIgnoreCase(String title);

    Optional<Service> findByGroupInfoIdAndId(Long groupId, Long id);

    @Query("SELECT s FROM Service s JOIN FETCH s.user WHERE s.groupInfo.id = :groupId")
    List<Service> findByGroupInfoIdWithUser(@Param("groupId") Long groupId);
}