package com.uaes.esw.gwmc30demo.domain.model.drivingModeSce;
import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.DrivingMode;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data @Builder
public class QueryDMRes {
    String dateTime;
    Driver driver;
    List<DrivingMode> drivingMode;
    String responseCode;
}
