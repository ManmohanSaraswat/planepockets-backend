package com.planepockets.controller;

import com.planepockets.AviationStackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1.0/flight")
public class FlightSearchController {

    @Autowired
    private AviationStackService aviationstackService;

    @GetMapping("/search")
    public String searchFlights(
            @RequestParam String start,
            @RequestParam String destination,
            @RequestParam String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(defaultValue = "1") int noOfPassengers) {

        return aviationstackService.searchFlights(start, destination, fromDate, toDate, noOfPassengers);
    }

    @GetMapping("/airports")
    public String getAirports() {
        return aviationstackService.getAirports();
    }

}
