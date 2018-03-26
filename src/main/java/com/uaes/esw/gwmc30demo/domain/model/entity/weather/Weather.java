package com.uaes.esw.gwmc30demo.domain.model.entity.weather;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Weather {
    String location;
    int weatherStatus;
    double temperature;
    double aqi;
}
