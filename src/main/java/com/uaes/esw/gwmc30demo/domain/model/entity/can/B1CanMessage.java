package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class B1CanMessage {
    long unixtimestamp;
    double Pack_Soc_BMS; //电池系统SOC
    double Pack_Curr_BMS; //电池包电流
    double Pack_Volt_BMS; //电池包电压
    double Pack_Temp_BMS; //电池系统温度
    int Pack_BalcSts_BMS; //电池系统均衡状态
    int Pack_ChrgSts_BMS; //电池系统充电状态
    String id;
}
