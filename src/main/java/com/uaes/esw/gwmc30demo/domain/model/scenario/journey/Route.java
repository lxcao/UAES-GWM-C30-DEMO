package com.uaes.esw.gwmc30demo.domain.model.scenario.journey;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Route {
    private int distance;
    private int expectTimeConsuming;
    private String label;
}
