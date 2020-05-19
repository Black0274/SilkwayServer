package ru.losev.silkway.controller.message.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildRouteRequest {

    private double sourceLat;
    private double sourceLng;
    private double destLat;
    private double destLng;
}
