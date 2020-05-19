package ru.losev.silkway.controller.message.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveRatingRequest {
    private Double lat;
    private Double lng;
    private Integer rating;
}
