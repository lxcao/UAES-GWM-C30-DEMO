package com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.DrivingMode;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.Vehicle;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data @Builder
public class QueryDMRes {
    String dateTime;
    Driver driver;
    List<DrivingMode> drivingMode;
    String responseCode;
    Vehicle vehicle;
}
