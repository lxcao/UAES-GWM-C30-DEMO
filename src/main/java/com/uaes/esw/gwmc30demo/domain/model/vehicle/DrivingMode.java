package com.uaes.esw.gwmc30demo.domain.model.vehicle;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DrivingMode {
    private String drivingModeType;
    private DrivingModeConfigure drivingModeConfigure;
}
