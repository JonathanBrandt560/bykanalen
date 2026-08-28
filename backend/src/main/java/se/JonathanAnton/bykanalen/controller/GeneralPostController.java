package se.JonathanAnton.bykanalen.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.dto.CreateGeneralPostDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostDetailDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostSummaryDTO;
import se.JonathanAnton.bykanalen.dto.PatchGeneralPostDTO;
import se.JonathanAnton.bykanalen.service.GeneralPostService;

import java.util.List;

/**
 * REST-controller för att hantera HTTP-förfrågningar kopplade till allmänna inlägg (GeneralPosts) i en grupp.
 * Alla anrop till denna controller startar med bas-URL:en /api/groups/{groupId}/generalposts
 */
@RestController
@RequestMapping("/api/groups/{groupId}/generalposts")
public class GeneralPostController {

    private final GeneralPostService generalPostService;

    public GeneralPostController(GeneralPostService generalPostService) {
        this.generalPostService = generalPostService;
    }

    // Endpoint som hämtar allmänna inlägg sorterat efter datum (nyast först)
    @GetMapping("/orderlatest")
    public ResponseEntity<List<GeneralPostSummaryDTO>> getAllLatestGeneralPosts(@PathVariable Long groupId) {
        return ResponseEntity.ok(generalPostService.getAllGeneralPostsLatest(groupId));
    }

    // Endpoint som hämtar allmänna inlägg sorterat efter flest likes
    @GetMapping("/orderlikes")
    public ResponseEntity<List<GeneralPostSummaryDTO>> getAllGeneralPostsByLikes(@PathVariable Long groupId) {
        return ResponseEntity.ok(generalPostService.getAllGeneralPostsByLikes(groupId));
    }

    /* Endpoint som hämtar det allmänna inlägg vars id specificerats
    Returnerar en detaljvy av det allmänna inlägget */
    @GetMapping("/{id}")
    public ResponseEntity<GeneralPostDetailDTO> getGeneralPostById(@PathVariable Long groupId, @PathVariable Long id) {
        return ResponseEntity.ok(generalPostService.getGeneralPostById(groupId, id));
    }

    /* Enpoint för att skapa ett nytt allmänt inlägg i en specifik grupp.
     @Valid Aktiverar automatisk validering av DTO:n (t.ex. att fält inte får vara tomma).
     @return ResponseEntity som innehåller det skapade allmänna inlägget (GeneralPostDetailDTO) och HTTP-status 201 Created.
     */
    @PostMapping
    public ResponseEntity<GeneralPostDetailDTO> createGeneralPost(
            @Valid @RequestBody CreateGeneralPostDTO dto,
            @PathVariable Long groupId) {
        return ResponseEntity.status(201).body(
                generalPostService.createGeneralPost(dto, groupId)
        );
    }

    // Endpoint som tar bort det allmänna inlägg vars id specificerats
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeneralPostById(@PathVariable Long groupId, @PathVariable Long id) {
        generalPostService.deleteGeneralPost(groupId, id);
        return ResponseEntity.noContent().build();
    }

    /* Endpoint som uppdaterar det allmänna inlägg vars id specificerats.
    Validerar de inmatade värdena i DTON:n (@Valid) */
    @PatchMapping("/{id}")
    public ResponseEntity<GeneralPostDetailDTO> patchGeneralPost(@PathVariable Long groupId,
                                                                 @PathVariable Long id,
                                                                 @Valid @RequestBody PatchGeneralPostDTO dto) {
        return ResponseEntity.ok(generalPostService.patchGeneralPost(groupId, id, dto));
    }
}