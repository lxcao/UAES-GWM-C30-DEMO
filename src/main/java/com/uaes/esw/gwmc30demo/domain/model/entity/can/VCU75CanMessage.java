package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU75CanMessage {
    long unixtimestamp;
    double HVBatt_Energy_Custom_Cyc;//自定义模式本次驾驶循环电池能量消耗
    double Motor_Energy_Custom_Cyc;//自定义模式本次驾驶循环电机能量消耗
    double DCDC_Energy_Custom_Cyc;//自定义模式本次驾驶循环DCDC能量消耗
    double PTC_Energy_Custom_Cyc;//自定义模式本次驾驶循环制热空调能量消耗
}
