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

    @GetMapping("/{id}")
    public ResponseEntity<GeneralPostDetailDTO> getGeneralPostById(@PathVariable Long groupId, @PathVariable Long id) {
        return ResponseEntity.ok(generalPostService.getGeneralPostById(groupId, id));
    }

    /**
     * HTTP POST-slutpunkt för att skapa ett nytt allmänt inlägg (GeneralPost) i en specifik grupp.
     *
     * @Valid Aktiverar automatisk validering av DTO:n (t.ex. att fält inte får vara tomma).
     * @RequestBody Omvandlar inkommande JSON-data från anropets body till ett CreateGeneralPostDTO-objekt.
     * @PathVariable Hämtar 'groupId' från URL-sökvägen.
     * @RequestParam Hämtar 'userId' från URL-parametern (query parameter).
     *
     * @return ResponseEntity som innehåller det skapade inlägget (GeneralPostDetailDTO) och HTTP-status 201 Created.
     */
    @PostMapping
    public ResponseEntity<GeneralPostDetailDTO> createGeneralPost(
            @Valid @RequestBody CreateGeneralPostDTO dto,
            @PathVariable Long groupId,
            @RequestParam Long userId) {
        return ResponseEntity.status(201).body(generalPostService.createGeneralPost(dto, groupId, userId));
    }
}