package se.JonathanAnton.bykanalen.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.JonathanAnton.bykanalen.model.GeneralPost;
import se.JonathanAnton.bykanalen.model.GeneralPostLike;
import se.JonathanAnton.bykanalen.repository.GeneralPostLikeRepository;
import se.JonathanAnton.bykanalen.repository.GeneralPostRepository;
import se.JonathanAnton.bykanalen.dto.LikeResult;

@Service
public class GeneralPostLikeService {

    private final GeneralPostLikeRepository likeRepository;
    private final GeneralPostRepository postRepository;

    public GeneralPostLikeService(GeneralPostLikeRepository likeRepository, GeneralPostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public LikeResult toggleLike(Long userId, Long postId) {
        GeneralPost post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post hittades inte: " + postId));

        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(userId, postId);

        if (alreadyLiked) {
            likeRepository.deleteByUserIdAndPostId(userId, postId);
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            likeRepository.save(new GeneralPostLike(userId, postId));
            post.setLikeCount(post.getLikeCount() + 1);
        }

        postRepository.save(post);
        return new LikeResult(!alreadyLiked, post.getLikeCount());
    }

    public boolean hasUserLiked(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}