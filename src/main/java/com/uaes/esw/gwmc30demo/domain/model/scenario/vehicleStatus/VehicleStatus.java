package com.uaes.esw.gwmc30demo.domain.model.scenario.vehicleStatus;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VehicleStatus {
    double VCU_RemainDistance; // 剩余续航里程
    int Pack_ChrgSts_BMS; //充电状态和车载充电器状态
    double Pack_ChrgReTime_BMS; //充电剩余时间
    int HV_PowerOn; //高压电池状态
    int TM_OperMod; //电机工作状态
    int DCDC_OperMod; //DCDC变压器工作状态
    int AC_OperMod; //空调状态 0: no AC; 1: PTC AC; 2: CMP AC;
}
