package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU70CanMessage {
    long unixtimestamp;
    double AC_Energy_Sport_Cyc;//Sport模式本次驾驶循环制冷空调能量消耗
    double Mileage_Sport_Cyc;//Sport模式本次驾驶循环行驶里程

    public VCU70CanMessage adding(VCU70CanMessage vcu70CanMessage){
        this.setAC_Energy_Sport_Cyc(this.getAC_Energy_Sport_Cyc()
                +vcu70CanMessage.getAC_Energy_Sport_Cyc());
        this.setMileage_Sport_Cyc(this.getMileage_Sport_Cyc()
                +vcu70CanMessage.getMileage_Sport_Cyc());
        this.setUnixtimestamp(vcu70CanMessage.getUnixtimestamp());
        return this;
    }
}
