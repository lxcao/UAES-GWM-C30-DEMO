package com.uaes.esw.gwmc30demo.domain.model.charger;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Charger {
    //类型 AC或者DC
    private String type;
    private double chargingPower;
}
