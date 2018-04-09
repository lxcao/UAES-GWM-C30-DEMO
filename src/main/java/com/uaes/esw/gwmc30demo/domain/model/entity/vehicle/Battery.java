package com.uaes.esw.gwmc30demo.domain.model.entity.vehicle;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Battery {
    double soc;
    double current; //电流
    double voltage; //电压
    double temperature; //温度
    int balanceStatus; //均衡状态
    int chargingStatus; //充电状态
    double socMax;
    double socMin;
    double chargingTime; //充电剩余时间
}
