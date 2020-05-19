package ru.losev.silkway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.losev.silkway.domain.Place;
import ru.losev.silkway.domain.PlaceRating;
import ru.losev.silkway.domain.User;

public interface PlaceRatingRepo extends JpaRepository<PlaceRating, Long> {

    @Query(value = "SELECT AVG(PR.rating) FROM PlaceRating PR WHERE PR.place = ?1")
    Double avgRatingByPlace(Place place);

    boolean existsByPlaceAndUser(Place place, User user);

    @Modifying
    @Query(value = "UPDATE PlaceRating PR SET PR.rating = ?1 WHERE PR.place = ?2 AND PR.user = ?3")
    void updateRating(Integer rating, Place place, User user);

    Integer countByPlace(Place place);
}
