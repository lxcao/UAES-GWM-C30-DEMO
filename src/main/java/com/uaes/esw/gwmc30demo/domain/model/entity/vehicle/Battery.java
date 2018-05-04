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
    int hvPower; //高压上电标志
    double remainDistance; // 剩余续航里程
    int tmOperMod; //电机工作状态
    int dcdcOperMod; //DCDC变压器工作状态
    double ptcPCnsmptn; // 空调制热
    double cmpPCnsmptn; // 空调制冷
}
