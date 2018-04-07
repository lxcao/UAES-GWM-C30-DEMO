package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU68CanMessage {
    long unixtimestamp;
    double AC_Energy_Norm_Cyc;//Normal模式本次驾驶循环制冷空调能量消耗
    double Mileage_Norm_Cyc;//Normal模式本次驾驶循环行驶里程

    public VCU68CanMessage adding(VCU68CanMessage vcu68CanMessage){
        this.setAC_Energy_Norm_Cyc(this.getAC_Energy_Norm_Cyc()
                +vcu68CanMessage.getAC_Energy_Norm_Cyc());
        this.setMileage_Norm_Cyc(this.getMileage_Norm_Cyc()
                +vcu68CanMessage.getMileage_Norm_Cyc());
        this.setUnixtimestamp(vcu68CanMessage.getUnixtimestamp());
        return this;
    }
}
