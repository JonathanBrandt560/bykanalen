package se.JonathanAnton.bykanalen.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.dto.CreateGeneralPostDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostDetailDTO;
import se.JonathanAnton.bykanalen.dto.GeneralPostSummaryDTO;
import se.JonathanAnton.bykanalen.service.GeneralPostService;

import java.util.List;

/**
 * REST-controller för att hantera HTTP-förfrågningar kopplade till allmänna inlägg (GeneralPosts) i en grupp.
 * Alla anrop till denna controller startar med bas-URL:en /groups/{groupId}/generalposts
 */
@RestController
@RequestMapping("/groups/{groupId}/generalposts")
public class GeneralPostController {

    private final GeneralPostService generalPostService;

    public GeneralPostController(GeneralPostService generalPostService) {
        this.generalPostService = generalPostService;
    }

    // Endpoint som hämtar allmänna inlägg sorterat efter datum (nyast först)
    @GetMapping("/orderlatest")
    public ResponseEntity<List<GeneralPostSummaryDTO>> getAllLatestGeneralPosts(@PathVariable Long groupId,
                                                                                @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(generalPostService.getAllGeneralPostsLatest(groupId, userDetails.getUsername()));
    }

    // Endpoint som hämtar allmänna inlägg sorterat efter flest likes
    @GetMapping("/orderlikes")
    public ResponseEntity<List<GeneralPostSummaryDTO>> getAllGeneralPostsByLikes(@PathVariable Long groupId,
                                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(generalPostService.getAllGeneralPostsByLikes(groupId, userDetails.getUsername()));
    }

    /* Endpoint som hämtar det allmänna inlägg vars id specificerats.
    Returnerar en detaljvy av foruminlägget */
    @GetMapping("/{id}")
    public ResponseEntity<GeneralPostDetailDTO> getGeneralPostById(@PathVariable Long groupId, @PathVariable Long id) {
        return ResponseEntity.ok(generalPostService.getGeneralPostById(groupId, id));
    }

    /* Enpoint för att skapa ett nytt allmänt inlägg i en specifik grupp.
     @Valid Aktiverar automatisk validering av DTO:n (t.ex. att fält inte får vara tomma).
     @RequestBody Omvandlar inkommande JSON-data från anropets body till ett CreateGeneralPostDTO-objekt.
     @PathVariable Hämtar 'groupId' från URL-sökvägen.
     @return ResponseEntity som innehåller det skapade allmänna inlägget (GeneralPostDetailDTO) och HTTP-status 201 Created.
     */
    @PostMapping
    public ResponseEntity<GeneralPostDetailDTO> createGeneralPost(
            @Valid @RequestBody CreateGeneralPostDTO dto,
            @PathVariable Long groupId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(201).body(
                generalPostService.createGeneralPost(dto, groupId, userDetails.getUsername())
        );
    }
}