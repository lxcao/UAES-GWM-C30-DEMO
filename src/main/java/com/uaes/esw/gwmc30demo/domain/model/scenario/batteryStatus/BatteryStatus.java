package com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BatteryStatus {
    double Pack_Soc_BMS; //电池系统SOC
    double Pack_Curr_BMS; //电池包电流
    double Pack_Volt_BMS; //电池包电压
    double Pack_Temp_BMS; //电池系统温度
    int Pack_ChrgSts_BMS; //电池系统充电状态
    double Pack_ChrgReTime_BMS; //电池系统充电剩余时间
    int Pack_BalcSts_BMS; //电池系统均衡状态
    double Pack_CellSocMax_BMS; //电池系统最高单体SOC
    double Pack_CellSOCMin_BMS; //电池系统最低单体SOC
    int Pack_ChrgReq_BMS; // 是否需要充电
}
