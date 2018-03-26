package com.uaes.esw.gwmc30demo.domain.model.entity.journey;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Journey {
    Location source;
    Location destination;
    List<Route> routes;
}
