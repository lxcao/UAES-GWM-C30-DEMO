package com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving;

import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class QueryESReq {
    Driver driver;
    String dateTime;
    String startDateTime;
    String endDateTime;
}
