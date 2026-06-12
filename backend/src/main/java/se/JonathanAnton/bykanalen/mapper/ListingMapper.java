package se.JonathanAnton.bykanalen.mapper;

import org.springframework.stereotype.Component;
import se.JonathanAnton.bykanalen.dto.ListingDTO;
import se.JonathanAnton.bykanalen.model.Listing;
import se.JonathanAnton.bykanalen.model.User;

@Component
public class ListingMapper {

      public ListingDTO toDTO(Listing listing) {
            return new ListingDTO(
                  listing.getId(),
                  listing.getTitle(),
                  listing.getDescription(),
                  listing.getImage(),
                  listing.getPublishDate(),
                  listing.getPrice(),
                  listing.getLocation(),
                  listing.getUser() != null ? listing.getUser().getId() : null
            );
      }

      public Listing toEntity(ListingDTO dto, User user) {
            Listing listing = new Listing();
                  listing.setTitle(dto.getTitle());
                  listing.setDescription(dto.getDescription());
                  listing.setImage(dto.getImage());
                  listing.setPublishDate(dto.getPublishDate());
                  listing.setPrice(dto.getPrice());
                  listing.setLocation(dto.getLocation());
                  listing.setUser(user);
                 return listing;
      }
}