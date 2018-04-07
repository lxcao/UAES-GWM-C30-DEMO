package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU64CanMessage {
    long unixtimestamp;
    double AC_Energy_UltraEco_Cyc;//UltraEco模式本次驾驶循环制冷空调能量消耗
    double Mileage_UltraEco_Cyc;//UltraEco模式本次驾驶循环行驶里程

    public VCU64CanMessage adding(VCU64CanMessage vcu64CanMessage){
        this.setAC_Energy_UltraEco_Cyc(this.getAC_Energy_UltraEco_Cyc()
                +vcu64CanMessage.getAC_Energy_UltraEco_Cyc());
        this.setMileage_UltraEco_Cyc(this.getMileage_UltraEco_Cyc()
                +vcu64CanMessage.getMileage_UltraEco_Cyc());
        this.setUnixtimestamp(vcu64CanMessage.getUnixtimestamp());
        return this;
    }
}
