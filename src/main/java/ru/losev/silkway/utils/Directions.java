package ru.losev.silkway.utils;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.losev.silkway.jwt.JwtProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Directions {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${silkway.app.googleApiKey}")
    private String apiKey;

    public List<LatLng> doRequest(double sourceLat, double sourceLng, double destLat, double destLng) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        DirectionsApiRequest request = DirectionsApi.getDirections(context,
                sourceLat + "," + sourceLng,
                destLat + "," + destLng);

        List<LatLng> path = new ArrayList<>();

        try {
            DirectionsResult result = request.await();

            if (result.routes != null && result.routes.length > 0) {
                DirectionsRoute route = result.routes[0];

                if (route.legs != null) {
                    for (int i = 0; i < route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j = 0; j < leg.steps.length; j++) {
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length > 0) {
                                    for (int k = 0; k < step.steps.length; k++) {
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of path coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of path coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ApiException | InterruptedException | IOException e) {
            logger.error(e.getMessage());
        }
        return path;
    }
}
