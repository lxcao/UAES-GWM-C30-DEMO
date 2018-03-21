package com.uaes.esw.gwmc30demo.domain.model.driver;
import lombok.Builder;
import lombok.Data;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.DrivingMode;

@Data @Builder
public class SetDMReq {
    String dateTime;
    Driver driver;
    DrivingMode drivingMode;
}
