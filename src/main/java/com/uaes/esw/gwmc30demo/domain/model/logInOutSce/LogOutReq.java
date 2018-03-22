package com.uaes.esw.gwmc30demo.domain.model.logInOutSce;
import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class LogOutReq {
    String dateTime;
    Driver driver;
}
