package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.JonathanAnton.bykanalen.model.ServiceUser;

import java.util.List;
import java.util.Optional;

public interface ServiceUserRepository extends JpaRepository<ServiceUser, Long> {

    // Hitta alla services en user använder
    List<ServiceUser> findByUserId(Long userId);

    // Hitta alla users som använder en service
    List<ServiceUser> findByServiceId(Long serviceId);

    // Kontrollera om en user redan är registrerad på en service
    boolean existsByUserIdAndServiceId(Long userId, Long serviceId);

    // Hitta en specifik registrering
    Optional<ServiceUser> findByUserIdAndServiceId(Long userId, Long serviceId);

    // Få antalet users för en service
    long countByServiceId(Long serviceId);

}
