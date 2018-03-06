package com.uaes.esw.gwmc30demo.domain.model.vehicle;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Vehicle {
    private String vin;
    private Battery battery;
    private double maxMileage;
}
