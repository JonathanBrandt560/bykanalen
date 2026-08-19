package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.JonathanAnton.bykanalen.enums.UserType;
import se.JonathanAnton.bykanalen.model.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    // Kontrollerar att användare finns och vilken behörighetstyp den har
    boolean existsByUserIdAndType(Long userId, UserType type);
}
