package se.JonathanAnton.bykanalen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.service.GeneralPostLikeService;
import se.JonathanAnton.bykanalen.dto.LikeResult;

/**
 * REST-controller för att hantera HTTP-förfrågningar kopplade till gillningar (Likes) av allmänna inlägg
 * (General posts) i en grupp.
 * Alla anrop till denna controller startar med bas-URL:en /groups/{groupId}/events
 */
@RestController
@RequestMapping("/groups/{groupId}/generalposts")
public class GeneralPostLikeController {

    private final GeneralPostLikeService likeService;

    public GeneralPostLikeController(GeneralPostLikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<LikeResult> toggleLike(@PathVariable Long groupId,
                                                 @PathVariable Long postId,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(likeService.toggleLike(groupId, postId, userDetails.getUsername()));
    }

    @GetMapping("/{postId}/like-status")
    public ResponseEntity<Boolean> getLikeStatus(@PathVariable Long groupId,
                                                 @PathVariable Long postId,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(likeService.hasUserLiked(groupId, postId, userDetails.getUsername()));
    }
}