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
    private final AuthorizationService authorizationService;

    public GeneralPostLikeService(GeneralPostLikeRepository likeRepository,
                                  GeneralPostRepository postRepository,
                                  AuthorizationService authorizationService) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.authorizationService = authorizationService;
    }

    // Hanterar logik för en like-knapp kopplat till ett allmänt inlägg. Returnerar utfallet av knapp-interaktionen.
    @Transactional
    public LikeResult toggleLike(Long groupId, Long postId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        GeneralPost post = postRepository.findByGroupInfoIdAndId(groupId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post med id " + postId + " hittades inte"));

        // Variabel som håller koll på om den inloggade användaren redan har like:at inlägget
        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(user.getId(), postId);

        /* Om användaren har like:at inlägget och klickar på like-knappen, tas like-sparningen bort.
        Och de totala antalet likes för inlägget minskar med 1 */
        if (alreadyLiked) {
            likeRepository.deleteByUserIdAndPostId(user.getId(), postId);
            post.setLikeCount(post.getLikeCount() - 1);
        /* Om användaren INTE har like:at inlägget och klickar på like-knappen, läggs en like-sparning till.
        Och de totala antalet likes för inlägget ökar med 1 */
        } else {
            likeRepository.save(new GeneralPostLike(user.getId(), postId));
            post.setLikeCount(post.getLikeCount() + 1);
        }

        postRepository.save(post);
        return new LikeResult(!alreadyLiked, post.getLikeCount());
    }

    public boolean hasUserLiked(Long groupId, Long postId) {
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        return likeRepository.existsByUserIdAndPostId(user.getId(), postId);
    }
}