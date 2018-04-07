package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU76CanMessage {
    long unixtimestamp;
    double AC_Energy_Custom_Cyc;//自定义模式本次驾驶循环制冷空调能量消耗
    double Mileage_Custom_Cyc;//自定义模式本次驾驶循环行驶里程

    public VCU76CanMessage adding(VCU76CanMessage vcu76CanMessage){
        this.setAC_Energy_Custom_Cyc(this.getAC_Energy_Custom_Cyc()
                +vcu76CanMessage.getAC_Energy_Custom_Cyc());
        this.setMileage_Custom_Cyc(this.getMileage_Custom_Cyc()
                +vcu76CanMessage.getMileage_Custom_Cyc());
        this.setUnixtimestamp(vcu76CanMessage.getUnixtimestamp());
        return this;
    }
}
