package ru.losev.silkway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.losev.silkway.domain.Place;

public interface PlaceRepo extends JpaRepository<Place, Long> {

    Place findByLatAndLng(Double lat, Double lng);
}
