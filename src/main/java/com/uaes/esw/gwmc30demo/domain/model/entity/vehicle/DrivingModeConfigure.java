package com.uaes.esw.gwmc30demo.domain.model.entity.vehicle;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DrivingModeConfigure {
    private int maxSpeed;
    private int energyRecovery;
    private int powerCorresponding;
    private int smoothness;
    private int accessoryPerformance;
}
