package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.JonathanAnton.bykanalen.model.Service;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    // Hitta alla tjänster kopplade till en grupp/by
    List<Service> findByGroupInfoId(Long groupId);

    List<Service> findAllByOrderByPublishDateDesc();

    List<Service> findByTitleContainingIgnoreCase(String title);
}