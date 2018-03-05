package com.uaes.esw.gwmc30demo.domain.model.vehicle;

import lombok.Data;

@Data
public class Battery {
    private String id;
    private int type;
    private int soc;
}
