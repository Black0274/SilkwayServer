package ru.losev.silkway.controller.message.request;

import com.google.maps.model.LatLng;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveRouteRequest {
    private List<LatLng> path;
}