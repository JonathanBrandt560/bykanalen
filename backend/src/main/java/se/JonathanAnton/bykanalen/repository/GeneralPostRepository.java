package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.JonathanAnton.bykanalen.model.GeneralPost;

import java.util.List;
import java.util.Optional;

public interface GeneralPostRepository extends JpaRepository<GeneralPost, Long> {

    // Hitta alla inlägg från en specifik user
    List<GeneralPost> findByUserIdOrderByPublishDateDesc(Long userId);

    // Hitta alla inlägg sorterade efter publiceringsdatum (senaste först)
    List<GeneralPost> findByGroupIdOrderByPublishDateDesc(Long groupId);

    // Hitta inlägg sorterade efter likes
    List<GeneralPost> findByGroupIdOrderByLikeCountDesc(Long groupId);

    // Hitta inlägg sorterat efter Id
    Optional<GeneralPost> findByIdAndGroupId(Long id, Long groupId);

}
