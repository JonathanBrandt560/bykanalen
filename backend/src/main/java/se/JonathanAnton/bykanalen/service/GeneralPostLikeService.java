package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.JonathanAnton.bykanalen.exception.ResourceNotFoundException;
import se.JonathanAnton.bykanalen.model.GeneralPost;
import se.JonathanAnton.bykanalen.model.GeneralPostLike;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.GeneralPostLikeRepository;
import se.JonathanAnton.bykanalen.repository.GeneralPostRepository;
import se.JonathanAnton.bykanalen.dto.LikeResult;
import se.JonathanAnton.bykanalen.repository.UserRepository;

@Service
public class GeneralPostLikeService {

    private final GeneralPostLikeRepository likeRepository;
    private final GeneralPostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public GeneralPostLikeService(GeneralPostLikeRepository likeRepository,
                                  GeneralPostRepository postRepository,
                                  UserRepository userRepository,
                                  AuthorizationService authorizationService) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public LikeResult toggleLike(Long groupId, Long postId, String username) {
        authorizationService.verifyGroupMembership(groupId);

        GeneralPost post = postRepository.findByGroupInfoIdAndId(groupId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post hittades inte: " + postId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Användare hittades inte"));

        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(user.getId(), postId);

        if (alreadyLiked) {
            likeRepository.deleteByUserIdAndPostId(user.getId(), postId);
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            likeRepository.save(new GeneralPostLike(user.getId(), postId));
            post.setLikeCount(post.getLikeCount() + 1);
        }

        postRepository.save(post);
        return new LikeResult(!alreadyLiked, post.getLikeCount());
    }

    public boolean hasUserLiked(Long groupId, Long postId, String username) {
        authorizationService.verifyGroupMembership(groupId);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Användare hittades inte"));

        return likeRepository.existsByUserIdAndPostId(user.getId(), postId);
    }
}