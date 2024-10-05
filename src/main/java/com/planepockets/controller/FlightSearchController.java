package com.planepockets.controller;

import com.amadeus.exceptions.ResponseException;
import com.planepockets.config.AmadeusConnect;
import com.amadeus.resources.Location;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1.0/flight")
public class FlightSearchController {

    @GetMapping("/locations")
    public Location[] locations(@RequestParam(required=true) String keyword) throws ResponseException {
        return AmadeusConnect.INSTANCE.location(keyword);
    }

}
