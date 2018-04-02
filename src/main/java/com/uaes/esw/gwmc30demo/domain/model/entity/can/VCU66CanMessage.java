package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU66CanMessage {
    long unixtimestamp;
    double AC_Energy_Eco_Cyc;//Eco模式本次驾驶循环制冷空调能量消耗
    double Mileage_Eco_Cyc;//Eco模式本次驾驶循环行驶里程
}
