package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU72CanMessage {
    long unixtimestamp;
    double AC_Energy_UltraSport_Cyc;//UltraSport模式本次驾驶循环制冷空调能量消耗
    double Mileage_UltraSport_Cyc;//UltraSport模式本次驾驶循环行驶里程
}
