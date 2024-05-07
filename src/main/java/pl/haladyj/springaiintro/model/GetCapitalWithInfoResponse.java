package pl.haladyj.springaiintro.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalWithInfoResponse(@JsonPropertyDescription("This is the city name") String city,
                                         @JsonPropertyDescription("This is the population of a city") String population,
                                         @JsonPropertyDescription("This is the region where city is situated") String region,
                                         @JsonPropertyDescription("This is the language spoken i n a city") String language,
                                         @JsonPropertyDescription("This is the currency paid in a nominated city") String currency) {
}
