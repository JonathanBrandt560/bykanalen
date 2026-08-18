package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.JonathanAnton.bykanalen.model.GeneralPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneralPostLikeRepository extends JpaRepository<GeneralPostLike, Long> {

    // Undersöker om allmännt inlägg redan blivit gillat av specifik användare
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    // Tar bort en gillning på ett allmännt inlägg
    void deleteByUserIdAndPostId(Long userId, Long postId);

    @Query("SELECT l.postId FROM GeneralPostLike l WHERE l.userId = :userId AND l.postId IN :postIds")
    List<Long> findLikedPostIds(@Param("userId") Long userId, @Param("postIds") List<Long> postIds);

    void deleteAllByPostId(Long postId);
}