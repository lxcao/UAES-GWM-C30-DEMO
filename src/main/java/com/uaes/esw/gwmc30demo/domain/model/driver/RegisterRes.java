package com.uaes.esw.gwmc30demo.domain.model.driver;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RegisterRes {
    String dateTime;
    Driver driver;
    String responseCode;
}
