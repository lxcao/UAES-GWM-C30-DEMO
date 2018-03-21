package com.uaes.esw.gwmc30demo.domain.model.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class B1CanMessage {
    long unixtimestamp;
    double pack_Soc_BMS;
    //String devicename;
    String id;
}
