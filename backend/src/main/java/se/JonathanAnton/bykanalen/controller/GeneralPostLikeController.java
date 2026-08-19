package se.JonathanAnton.bykanalen.controller;

import org.springframework.http.ResponseEntity;
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

    // Endpoint som hanterar like-knapp-input från användare för ett specifikt allmänt inlägg
    @PostMapping("/{postId}/like")
    public ResponseEntity<LikeResult> toggleLike(@PathVariable Long groupId, @PathVariable Long postId) {
        return ResponseEntity.ok(likeService.toggleLike(groupId, postId));
    }

    // Endpoint som hämtar like-info för en specifik användare och ett specifikt allmänt inlägg
    @GetMapping("/{postId}/like-status")
    public ResponseEntity<Boolean> getLikeStatus(@PathVariable Long groupId, @PathVariable Long postId) {
        return ResponseEntity.ok(likeService.hasUserLiked(groupId, postId));
    }
}