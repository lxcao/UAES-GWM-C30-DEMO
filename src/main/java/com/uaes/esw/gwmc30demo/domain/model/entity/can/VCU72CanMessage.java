package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU72CanMessage {
    long unixtimestamp;
    double AC_Energy_UltraSport_Cyc;//UltraSport模式本次驾驶循环制冷空调能量消耗
    double Mileage_UltraSport_Cyc;//UltraSport模式本次驾驶循环行驶里程

    public VCU72CanMessage adding(VCU72CanMessage vcu72CanMessage){
        this.setAC_Energy_UltraSport_Cyc(this.getAC_Energy_UltraSport_Cyc()
                +vcu72CanMessage.getAC_Energy_UltraSport_Cyc());
        this.setMileage_UltraSport_Cyc(this.getMileage_UltraSport_Cyc()
                +vcu72CanMessage.getMileage_UltraSport_Cyc());
        this.setUnixtimestamp(vcu72CanMessage.getUnixtimestamp());
        return this;
    }
}
