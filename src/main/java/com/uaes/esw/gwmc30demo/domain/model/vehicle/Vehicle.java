package com.uaes.esw.gwmc30demo.domain.model.vehicle;

import lombok.Data;

@Data
public class Vehicle {
    private String vin;
    private Battery battery;
    private double maxMileage;
}
