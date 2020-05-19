package ru.losev.silkway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.losev.silkway.domain.Place;
import ru.losev.silkway.domain.PlaceList;
import ru.losev.silkway.domain.User;

import java.util.List;

public interface PlaceListRepo extends JpaRepository<PlaceList, Long> {

    @Query("SELECT PL.place FROM PlaceList PL WHERE PL.user = ?1")
    List<Place> getPlacesByUser(User user);

    @Modifying
    @Query("DELETE FROM PlaceList PL WHERE PL.place = ?1 AND PL.user = ?2")
    void deleteByPlaceAndUser(Place place, User user);
}
