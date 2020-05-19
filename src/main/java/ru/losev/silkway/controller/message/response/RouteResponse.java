package ru.losev.silkway.controller.message.response;

import com.google.maps.model.LatLng;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RouteResponse {
    private List<LatLng> path;
}
