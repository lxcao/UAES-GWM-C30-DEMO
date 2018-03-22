package com.uaes.esw.gwmc30demo.domain.model.journeySce;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Journey {
    private Location source;
    private Location destination;
    private List<Route> routes;
}
