package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU62CanMessage {
    long unixtimestamp;
    double AC_Energy_Sum_Cyc;//本次驾驶循环制冷空调能量消耗
    double Mileage_Cyc;//本次驾驶循环行驶里程
    double Friction_Brk_Energy_Sum_Cyc;//本次驾驶循环摩擦制动消耗的能量
}
