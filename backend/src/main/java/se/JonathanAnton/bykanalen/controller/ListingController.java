package se.JonathanAnton.bykanalen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.JonathanAnton.bykanalen.dto.ListingDTO;
import se.JonathanAnton.bykanalen.service.ListingService;

import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController {

      private final ListingService listingService;

      public ListingController(ListingService listingService) {
            this.listingService = listingService;
      }

      @GetMapping
      public ResponseEntity<List<ListingDTO>> getAllListings() {
            return ResponseEntity.ok(listingService.getAllListings());
      }

      @GetMapping("/{id}")
      public ResponseEntity<ListingDTO> getListingById(@PathVariable Long id) {
            return ResponseEntity.ok(listingService.getListingById(id));
      }

      @GetMapping("/user/{userId}")
      public ResponseEntity<List<ListingDTO>> getListingsByUser(@PathVariable Long userId) {
            return ResponseEntity.ok(listingService.getListingsByUser(userId));
      }

      @GetMapping("/location/{location}")
      public ResponseEntity<List<ListingDTO>> getListingsByLocation(@PathVariable String location) {
            return ResponseEntity.ok(listingService.getListingsByLocation(location));
      }

      @GetMapping("/price")
      public ResponseEntity<List<ListingDTO>> getListingsByPriceRange(
            @RequestParam Integer min,
            @RequestParam Integer max
      ) {
            return ResponseEntity.ok(listingService.getListingsByPriceRange(min, max));
      }

      @PostMapping
      public ResponseEntity<ListingDTO> createListing(
            @RequestBody ListingDTO dto,
            @AuthenticationPrincipal UserDetails userDetails
      ) {
            return ResponseEntity.status(201).body(listingService.createListing(dto, userDetails.getUsername()));
      }

      @PutMapping("/{id}")
      public ResponseEntity<ListingDTO> updateListing(
            @PathVariable Long id,
            @RequestBody ListingDTO dto
      ) {
            return ResponseEntity.ok(listingService.updateListing(id, dto));
      }

      @DeleteMapping("/{id}")
      public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
            listingService.deleteListing(id);
            return ResponseEntity.noContent().build();
      }
}