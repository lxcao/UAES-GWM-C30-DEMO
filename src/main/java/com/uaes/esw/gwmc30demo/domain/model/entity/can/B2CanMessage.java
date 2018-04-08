package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class B2CanMessage {
    long unixtimestamp;
    double Pack_CellSocMax_BMS; //电池系统最高单体SOC
    double Pack_CellSocMin_BMS; //电池系统最低单体SOC
    double Pack_ChrgReTime_BMS; //电池系统充电剩余时间
}
