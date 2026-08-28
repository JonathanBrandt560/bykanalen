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

/** Service-lager för hantering av gillningar (likes) på allmänna inlägg i Bykanalen.
 Håller reda på vilka användare som gillat vilka inlägg, och synkar det aggregerade
 antalet gillningar (likeCount) på själva inlägget. */
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
        // Säkerställer att endast inloggade medlemmar av gruppen kan gilla/avgilla inlägg i den
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        GeneralPost post = postRepository.findByGroupInfoIdAndId(groupId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post med id " + postId + " hittades inte"));

        // Håller koll på om den inloggade användaren redan har gillat inlägget
        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(user.getId(), postId);

        // Om användaren redan gillat inlägget: ta bort gillningen och minska räknaren
        if (alreadyLiked) {
            likeRepository.deleteByUserIdAndPostId(user.getId(), postId);
            post.setLikeCount(post.getLikeCount() - 1);
        // Annars: lägg till en ny gillning och öka räknaren
        } else {
            likeRepository.save(new GeneralPostLike(user.getId(), postId));
            post.setLikeCount(post.getLikeCount() + 1);
        }

        postRepository.save(post);
        return new LikeResult(!alreadyLiked, post.getLikeCount());
    }

    /* Kontrollerar om den inloggade användaren redan har gillat ett specifikt inlägg.
    Används t.ex. för att visa rätt initialt tillstånd på gilla-knappen i frontend. */
    public boolean hasUserLiked(Long groupId, Long postId) {
        // Säkerställer att endast inloggade medlemmar av gruppen kan gilla/avgilla inlägg i den
        User user = authorizationService.getCurrentUser();
        authorizationService.verifyGroupMembership(groupId, user.getId());

        return likeRepository.existsByUserIdAndPostId(user.getId(), postId);
    }
}