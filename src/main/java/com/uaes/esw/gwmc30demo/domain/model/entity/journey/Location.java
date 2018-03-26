package com.uaes.esw.gwmc30demo.domain.model.entity.journey;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Location {
    private String locationName;
    //经度
    private String lng;
    //纬度
    private String lat;
}
