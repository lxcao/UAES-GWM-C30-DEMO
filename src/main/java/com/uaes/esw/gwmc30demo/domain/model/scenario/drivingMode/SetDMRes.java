package com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class SetDMRes {
    String dateTime;
    Driver driver;
    String responseCode;
}
