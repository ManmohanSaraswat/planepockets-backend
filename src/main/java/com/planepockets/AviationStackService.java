package com.planepockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class AviationStackService {

    private static final Logger logger = LoggerFactory.getLogger(AviationStackService.class);


    @Value("${aviationstack.api.key}")
    private String apiKey;

    @Value("${aviationstack.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String searchFlights(String start, String destination, String fromDate, String toDate, int passengers) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("access_key", apiKey)
                .queryParam("dep_iata", start)
                .queryParam("arr_iata", destination)
                .queryParam("flight_date", fromDate);

        String url = builder.toUriString();

        // Fetch flight data
        String response = restTemplate.getForObject(url, String.class);

        return response; // Return JSON string or parse it as needed
    }

    public String getAirports() {
        // Construct the URL to fetch the list of airports
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl.replace("/flights", "/airports"))
                .queryParam("access_key", apiKey)
                .toUriString();

        // Call the Aviationstack API to get airport data
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        logger.info("Aviationstack API Response: {}", response);
        // Extract the data part of the response (contains the list of airports)
        List<Map<String, Object>> airports = (List<Map<String, Object>>) response.get("data");

        logger.info("First airport entry: {}", airports.get(0));
        // Convert the list to a JSON-like string (for simplicity)
        StringBuilder airportsList = new StringBuilder();
        for (Map<String, Object> airport : airports) {
            String airportName = (String) airport.get("airport_name");
            String iataCode = (String) airport.get("iata_code");
            String cityName = airport.containsKey("city_iata_code") ? (String) airport.get("city_iata_code") : "Unknown City";
            String countryName = (String) airport.get("country_name");

            airportsList.append("Airport: ")
                    .append(airportName)
                    .append(" (")
                    .append(iataCode)
                    .append("), City IATA: ")
                    .append(cityName)
                    .append(", Country: ")
                    .append(countryName)
                    .append("\n");
        }

        return airportsList.toString();
    }
}
