package com.uaes.esw.gwmc30demo.domain.model.drivingModeSce;
import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;
import lombok.Builder;
import lombok.Data;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.DrivingMode;

@Data @Builder
public class SetDMReq {
    String dateTime;
    Driver driver;
    DrivingMode drivingMode;
}
