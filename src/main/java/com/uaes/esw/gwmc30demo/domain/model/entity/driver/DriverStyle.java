package com.uaes.esw.gwmc30demo.domain.model.entity.driver;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DriverStyle {
    String defaultDrivingMode;
    String currentDrivingMode;
}
