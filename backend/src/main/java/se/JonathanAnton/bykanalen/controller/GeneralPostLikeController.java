package se.JonathanAnton.bykanalen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.service.GeneralPostLikeService;
import se.JonathanAnton.bykanalen.dto.LikeResult;

@RestController
@RequestMapping("/api/general-posts")
public class GeneralPostLikeController {

    private final GeneralPostLikeService likeService;

    public GeneralPostLikeController(GeneralPostLikeService likeService) {
        this.likeService = likeService;
    }

    // TODO: byt ut @RequestParam userId mot inloggad användare (t.ex. via Authentication/JWT) när auth är klart
    @PostMapping("/{postId}/like")
    public ResponseEntity<LikeResult> toggleLike(@PathVariable Long postId, @RequestParam Long userId) {
        return ResponseEntity.ok(likeService.toggleLike(userId, postId));
    }

    @GetMapping("/{postId}/like-status")
    public ResponseEntity<Boolean> getLikeStatus(@PathVariable Long postId, @RequestParam Long userId) {
        return ResponseEntity.ok(likeService.hasUserLiked(userId, postId));
    }
}