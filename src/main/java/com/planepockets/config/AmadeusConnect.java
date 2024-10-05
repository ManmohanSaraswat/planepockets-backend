package com.planepockets.config;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.referenceData.Locations;
import com.amadeus.resources.Location;
import com.amadeus.exceptions.ResponseException;

public enum AmadeusConnect {
    INSTANCE;
    private Amadeus amadeus;

    private AmadeusConnect() {
        this.amadeus = Amadeus
                .builder("j9PiFGdnAIjMADRaZdyAq0oTLi2QLHVA", "BWR5Mb2LwJ7Gc8IR")
                .build();
    }

    public Location[] location(String keyword) throws ResponseException {
        return amadeus.referenceData.locations.get(Params
                .with("keyword", keyword)
                .and("subType", Locations.AIRPORT));
    }
}