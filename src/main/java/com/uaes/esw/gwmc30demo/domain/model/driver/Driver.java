package com.uaes.esw.gwmc30demo.domain.model.driver;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Driver {
    private long id;

    private String cellPhone;
    private String password;
}
