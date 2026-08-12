package se.JonathanAnton.bykanalen.repository;

import se.JonathanAnton.bykanalen.model.GeneralPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralPostLikeRepository extends JpaRepository<GeneralPostLike, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}