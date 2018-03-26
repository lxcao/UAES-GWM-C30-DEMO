package com.uaes.esw.gwmc30demo.domain.model.entity.journey;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Route {
    private int distance;
    private int expectTimeConsuming;
    private String label;
}
