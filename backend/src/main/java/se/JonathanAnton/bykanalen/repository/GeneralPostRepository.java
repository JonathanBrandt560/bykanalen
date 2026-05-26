package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.JonathanAnton.bykanalen.model.GeneralPost;

import java.util.List;

public interface GeneralPostRepository extends JpaRepository<GeneralPost, Long> {

    // Hitta alla inlägg från en specifik user
    List<GeneralPost> findByUserIdOrderByPublishDateDesc(Long userId);

    // Hitta alla inlägg sorterade efter publiceringsdatum (senaste först)
    List<GeneralPost> findAllByOrderByPublishDateDesc();

    // Hitta inlägg sorterade efter likes
    List<GeneralPost> findAllByOrderByLikeCountDesc();

}
