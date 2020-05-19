package ru.losev.silkway.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.losev.silkway.controller.message.request.BuildRouteRequest;
import ru.losev.silkway.controller.message.request.SaveRouteRequest;
import ru.losev.silkway.controller.message.response.RouteResponse;
import ru.losev.silkway.domain.User;
import ru.losev.silkway.repos.PointRepo;
import ru.losev.silkway.utils.Directions;
import ru.losev.silkway.utils.Utils;
import ru.losev.silkway.utils.dao.PointDAO;

@RestController
@RequestMapping("/route")
public class RouteRest {

    @Autowired
    private PointRepo pointRepo;

    @Autowired
    private Directions directions;

    @Autowired
    private PointDAO pointDAO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public RouteResponse route(@AuthenticationPrincipal User user) {
        RouteResponse response = new RouteResponse();
        response.setPath(Utils.convert(pointRepo.findByUserId(user.getId())));
        return response;
    }

    @PostMapping(value = "/show", produces = MediaType.APPLICATION_JSON_VALUE)
    public RouteResponse show(@RequestBody BuildRouteRequest request) {
        RouteResponse response = new RouteResponse();
        response.setPath(directions.doRequest(
                request.getSourceLat(),
                request.getSourceLng(),
                request.getDestLat(),
                request.getDestLng()));

        return response;
    }

    @PostMapping(value = "/build", produces = MediaType.APPLICATION_JSON_VALUE)
    public RouteResponse build(@AuthenticationPrincipal User user, @RequestBody BuildRouteRequest request) {
        RouteResponse response = new RouteResponse();
        response.setPath(directions.doRequest(
                request.getSourceLat(),
                request.getSourceLng(),
                request.getDestLat(),
                request.getDestLng()));

        pointDAO.delete(user.getId());
        pointDAO.insert(response.getPath(), user.getId().toString());

        return response;
    }


    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@AuthenticationPrincipal User user, @RequestBody SaveRouteRequest route) {
        pointDAO.delete(user.getId());
        pointDAO.insert(route.getPath(), user.getId().toString());

        return ResponseEntity.ok("success");
    }
}
