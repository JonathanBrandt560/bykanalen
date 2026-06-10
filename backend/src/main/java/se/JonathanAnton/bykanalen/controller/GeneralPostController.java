package se.JonathanAnton.bykanalen.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.dto.CreateGeneralPostDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostDetailDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostSummaryDTO;
import se.JonathanAnton.bykanalen.service.GeneralPostService;

import java.util.List;

@RestController
@RequestMapping("/groups/{groupId}/generalposts")
public class GeneralPostController {

    private final GeneralPostService generalPostService;

    public GeneralPostController(GeneralPostService generalPostService) {
        this.generalPostService = generalPostService;
    }

    @GetMapping("/orderlatest")
    public ResponseEntity<List<GeneralPostSummaryDTO>> getAllLatestGeneralPosts(@PathVariable Long groupId) {
        return ResponseEntity.ok(generalPostService.getAllGeneralPostsLatest(groupId));
    }

    @GetMapping("/orderlikes")
    public ResponseEntity<List<GeneralPostSummaryDTO>> getAllGeneralPostsByLikes(@PathVariable Long groupId) {
        return ResponseEntity.ok(generalPostService.getAllGeneralPostsByLikes(groupId));
    }

    @GetMapping("{id}")
    public ResponseEntity<GeneralPostDetailDTO> getGeneralPostById(@PathVariable Long groupId,@PathVariable Long id) {
        return ResponseEntity.ok(generalPostService.getGeneralPostById(groupId, id));
    }

    @PostMapping
    public ResponseEntity<GeneralPostDetailDTO> createGeneralPost(@Valid @RequestBody CreateGeneralPostDTO dto, @PathVariable Long groupId, Long userId) {
        return ResponseEntity.status(201).body(generalPostService.createGeneralPost(dto, groupId, userId));
    }
}
