package ru.losev.silkway.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "place_rating")
@Getter
@Setter
@NoArgsConstructor
public class PlaceRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public PlaceRating(Integer rating, Place place, User user) {
        this.rating = rating;
        this.place = place;
        this.user = user;
    }
}
