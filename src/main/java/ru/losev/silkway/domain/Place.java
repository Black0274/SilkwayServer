package ru.losev.silkway.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Place {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Double lat;
    private Double lng;

    private String title;
    private String description;
    private Integer type;

    @JsonInclude
    @Transient
    private Double rating;

    @JsonInclude
    @Transient
    private Integer ratingCount;

    @JsonInclude
    @Transient
    private String author;
}
