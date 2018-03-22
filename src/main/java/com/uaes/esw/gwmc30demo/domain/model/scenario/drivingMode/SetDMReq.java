package com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import lombok.Builder;
import lombok.Data;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.DrivingMode;

@Data @Builder
public class SetDMReq {
    String dateTime;
    Driver driver;
    DrivingMode drivingMode;
}
