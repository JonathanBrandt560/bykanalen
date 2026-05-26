package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.JonathanAnton.bykanalen.model.Listing;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    // Hitta alla annonser från en specifik user
    List<Listing> findByUserIdOrderByPublishDateDesc(Long userId);

    // Hitta alla annonser sorterade efter senaste
    List<Listing> findAllByOrderByPublishDateDesc();

    // Hitta annonser på en specifik plats
    List<Listing> findByLocationOrderByPublishDateDesc(String location);

    // Hitta annonser inom ett prisintervall
    List<Listing> findByPriceBetweenOrderByPriceAsc(Integer minPrice, Integer maxPrice);

}
