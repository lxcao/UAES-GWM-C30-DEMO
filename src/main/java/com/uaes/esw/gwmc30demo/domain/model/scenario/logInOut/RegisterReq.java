package com.uaes.esw.gwmc30demo.domain.model.scenario.logInOut;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RegisterReq {
    String dateTime;
    Driver driver;
}
