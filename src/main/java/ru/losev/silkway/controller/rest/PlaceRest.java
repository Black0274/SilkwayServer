package ru.losev.silkway.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.losev.silkway.controller.message.request.SaveRatingRequest;
import ru.losev.silkway.controller.message.response.PlaceResponse;
import ru.losev.silkway.controller.message.response.RatingResponse;
import ru.losev.silkway.domain.Place;
import ru.losev.silkway.domain.PlaceList;
import ru.losev.silkway.domain.PlaceRating;
import ru.losev.silkway.domain.User;
import ru.losev.silkway.repos.PlaceListRepo;
import ru.losev.silkway.repos.PlaceRatingRepo;
import ru.losev.silkway.repos.PlaceRepo;

import java.util.List;

@RestController
@RequestMapping("/place")
public class PlaceRest {

    @Autowired
    private PlaceRepo placeRepo;

    @Autowired
    private PlaceRatingRepo placeRatingRepo;

    @Autowired
    private PlaceListRepo placeListRepo;

    @GetMapping("/all")
    public PlaceResponse getAll() {
        List<Place> places = placeRepo.findAll();
        return prepareList(places);
    }

    @PostMapping()
    public ResponseEntity<String> savePlace(@AuthenticationPrincipal User user, @RequestBody Place place) {
        place.setUser(user);
        placeRepo.save(place);
        return ResponseEntity.ok("success");
    }

    @Transactional
    @PostMapping("/rating")
    public RatingResponse saveRating(@AuthenticationPrincipal User user, @RequestBody SaveRatingRequest req) {
        Place place = placeRepo.findByLatAndLng(req.getLat(), req.getLng());
        boolean alreadyExists = placeRatingRepo.existsByPlaceAndUser(place, user);

        if (alreadyExists) {
            placeRatingRepo.updateRating(req.getRating(), place, user);
        } else {
            placeRatingRepo.save(new PlaceRating(req.getRating(), place, user));
        }

        Double rating = placeRatingRepo.avgRatingByPlace(place);
        Integer ratingCount = placeRatingRepo.countByPlace(place);

        return new RatingResponse(rating, ratingCount);
    }

    @GetMapping("/list")
    public PlaceResponse list(@AuthenticationPrincipal User user) {
        List<Place> places = placeListRepo.getPlacesByUser(user);
        return prepareList(places);
    }

    @PostMapping("/list")
    public ResponseEntity<String> addAtList(@AuthenticationPrincipal User user, @RequestBody Place placeRq) {
        Place place = placeRepo.findByLatAndLng(placeRq.getLat(), placeRq.getLng());
        PlaceList placeList = new PlaceList(place, user);
        placeListRepo.save(placeList);
        return ResponseEntity.ok("success");
    }

    @Transactional
    @DeleteMapping("/list")
    public ResponseEntity<String> deleteFromList(@AuthenticationPrincipal User user, @RequestBody Place placeRq) {
        Place place = placeRepo.findByLatAndLng(placeRq.getLat(), placeRq.getLng());
        placeListRepo.deleteByPlaceAndUser(place, user);
        return ResponseEntity.ok("success");
    }

    private PlaceResponse prepareList(List<Place> places) {
        for (Place place : places) {
            place.setAuthor(place.getUser().getUsername());
            place.setRating(placeRatingRepo.avgRatingByPlace(place));
            place.setRatingCount(placeRatingRepo.countByPlace(place));
        }
        return new PlaceResponse(places);
    }
}
