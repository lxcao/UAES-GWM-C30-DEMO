package com.uaes.esw.gwmc30demo.domain.model.entity.driver;

import lombok.Builder;
import lombok.Data;


@Data @Builder
public class Driver {
    String cellPhone;
    String password;
    //List<String> vehicles;
    //Map<String, DriverStyle> driverStyles;
    String vin;
    String currentDM;
    String defaultDM;
}
