package com.uaes.esw.gwmc30demo.domain.model.drivingModeSce;
import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class QueryDMReq {
    String dateTime;
    Driver driver;

}
