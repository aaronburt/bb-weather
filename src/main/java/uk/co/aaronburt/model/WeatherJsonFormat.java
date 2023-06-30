package uk.co.aaronburt.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherJsonFormat {

    @Data
    @Builder
    @Jacksonized
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class Main {
        private final float temp;

        @JsonProperty("feels_like")
        private final float feelsLike;

        @JsonProperty("temp_min")
        private final float tempMin;

        @JsonProperty("temp_max")
        private final float tempMax;

        private final int pressure;

        private final int humidity;
    }
    @Data
    @Builder
    @Jacksonized
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private final int id;
        private final String main;
        private final String description;
        private final String icon;
    }

    private final List<Weather> weather;
    private final Main main;

    @JsonProperty("name")
    private final String city;

}
