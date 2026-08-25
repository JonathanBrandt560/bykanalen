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

      //Hämtar alla annonser
      @GetMapping
      public ResponseEntity<List<ListingDTO>> getAllListings() {
            return ResponseEntity.ok(listingService.getAllListings());
      }

      //Hämtar en specifik annons med id
      @GetMapping("/{id}")
      public ResponseEntity<ListingDTO> getListingById(@PathVariable Long id) {
            return ResponseEntity.ok(listingService.getListingById(id));
      }

      // Hämtar alla annonser som tillhör en specifik användare
      @GetMapping("/user/{userId}")
      public ResponseEntity<List<ListingDTO>> getListingsByUser(@PathVariable Long userId) {
            return ResponseEntity.ok(listingService.getListingsByUser(userId));
      }

      // Hämtar alla annonser för en given plats/ort
      @GetMapping("/location/{location}")
      public ResponseEntity<List<ListingDTO>> getListingsByLocation(@PathVariable String location) {
            return ResponseEntity.ok(listingService.getListingsByLocation(location));
      }

      // Hämtar annonser inom ett pris-intervall (min/max som query-params)
      @GetMapping("/price")
      public ResponseEntity<List<ListingDTO>> getListingsByPriceRange(
            @RequestParam Integer min,
            @RequestParam Integer max
      ) {
            return ResponseEntity.ok(listingService.getListingsByPriceRange(min, max));
      }

      // Skapar en ny annons; kopplas till den inloggade användaren via userDetails
      @PostMapping
      public ResponseEntity<ListingDTO> createListing(
            @RequestBody ListingDTO dto,
            @AuthenticationPrincipal UserDetails userDetails
      ) {
            return ResponseEntity.status(201).body(listingService.createListing(dto, userDetails.getUsername()));
      }

      // Uppdaterar en befintlig annons med angivet id
      @PutMapping("/{id}")
      public ResponseEntity<ListingDTO> updateListing(
            @PathVariable Long id,
            @RequestBody ListingDTO dto
      ) {
            return ResponseEntity.ok(listingService.updateListing(id, dto));
      }

      // Tar bort en annons med angivet id, returnerar 204 No Content
      @DeleteMapping("/{id}")
      public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
            listingService.deleteListing(id);
            return ResponseEntity.noContent().build();
      }
}