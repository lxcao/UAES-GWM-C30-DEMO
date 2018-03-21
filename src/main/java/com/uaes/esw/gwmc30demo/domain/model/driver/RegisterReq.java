package com.uaes.esw.gwmc30demo.domain.model.driver;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RegisterReq {
    String dateTime;
    Driver driver;
}
