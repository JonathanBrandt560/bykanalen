package se.JonathanAnton.bykanalen.service;

import org.springframework.stereotype.Service;
import se.JonathanAnton.bykanalen.dto.ListingDTO;
import se.JonathanAnton.bykanalen.mapper.ListingMapper;
import se.JonathanAnton.bykanalen.model.Listing;
import se.JonathanAnton.bykanalen.model.User;
import se.JonathanAnton.bykanalen.repository.ListingRepository;
import se.JonathanAnton.bykanalen.repository.UserRepository;

import java.util.List;

@Service
public class ListingService {

      private final ListingRepository listingRepository;
      private final UserRepository userRepository;
      private final ListingMapper listingMapper;

      public ListingService(ListingRepository listingRepository, UserRepository userRepository, ListingMapper listingMapper) {
            this.listingRepository = listingRepository;
            this.userRepository = userRepository;
            this.listingMapper = listingMapper;
      }

      public List<ListingDTO> getAllListings() {
            return listingRepository.findAllByOrderByPublishDateDesc()
                  .stream()
                  .map(listingMapper::toDTO)
                  .toList();
      }

      public ListingDTO getListingById(Long id) {
            Listing listing = listingRepository.findById(id)
                  .orElseThrow(() -> new RuntimeException("Annons hittades inte med id: " + id));
            return listingMapper.toDTO(listing);
      }

      public List<ListingDTO> getListingsByUser(Long userId) {
            return listingRepository.findByUserIdOrderByPublishDateDesc(userId)
                  .stream()
                  .map(listingMapper::toDTO)
                  .toList();
      }

      public List<ListingDTO> getListingsByLocation(String location) {
            return listingRepository.findByLocationOrderByPublishDateDesc(location)
                  .stream()
                  .map(listingMapper::toDTO)
                  .toList();
      }

      public List<ListingDTO> getListingsByPriceRange(Integer min, Integer max) {
            return listingRepository.findByPriceBetweenOrderByPriceAsc(min, max)
                  .stream()
                  .map(listingMapper::toDTO)
                  .toList();
      }

      public ListingDTO createListing(ListingDTO dto, String username) {
            User user = userRepository.findByUsername(username)
                  .orElseThrow(() -> new RuntimeException("Användare hittades inte: " + username));
            Listing listing = listingMapper.toEntity(dto, user);
            return listingMapper.toDTO(listingRepository.save(listing));
      }

      public ListingDTO updateListing(Long id, ListingDTO dto) {
            Listing listing = listingRepository.findById(id)
                  .orElseThrow(() -> new RuntimeException("Annons hittades inte med id: " + id));
            listing.setTitle(dto.getTitle());
            listing.setDescription(dto.getDescription());
            listing.setImage(dto.getImage());
            listing.setPrice(dto.getPrice());
            listing.setLocation(dto.getLocation());
            return listingMapper.toDTO(listingRepository.save(listing));
      }

      public void deleteListing(Long id) {
            listingRepository.deleteById(id);
      }
}