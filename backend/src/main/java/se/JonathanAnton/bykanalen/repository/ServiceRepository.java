package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.JonathanAnton.bykanalen.model.Service;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    // Hitta alla services sorterade efter senaste publicering
    List<Service> findAllByOrderByPublishDateDesc();

    // Hitta service filtrerat på titel
    List<Service> findByTitleContainingIgnoreCase(String title);

}
