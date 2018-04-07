package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU65CanMessage {
    long unixtimestamp;
    double HVBatt_Energy_Eco_Cyc;//Eco模式本次驾驶循环电池能量消耗
    double Motor_Energy_Eco_Cyc;//Eco模式本次驾驶循环电机能量消耗
    double DCDC_Energy_Eco_Cyc;//Eco模式本次驾驶循环DCDC能量消耗
    double PTC_Energy_Eco_Cyc;//Eco模式本次驾驶循环制热空调能量消耗

    public VCU65CanMessage adding(VCU65CanMessage vcu65CanMessage){
        this.setHVBatt_Energy_Eco_Cyc(this.getHVBatt_Energy_Eco_Cyc()
                +vcu65CanMessage.getHVBatt_Energy_Eco_Cyc());
        this.setMotor_Energy_Eco_Cyc(this.getMotor_Energy_Eco_Cyc()
                +vcu65CanMessage.getMotor_Energy_Eco_Cyc());
        this.setDCDC_Energy_Eco_Cyc(this.getDCDC_Energy_Eco_Cyc()
                +vcu65CanMessage.getDCDC_Energy_Eco_Cyc());
        this.setPTC_Energy_Eco_Cyc(this.getPTC_Energy_Eco_Cyc()
                +vcu65CanMessage.getPTC_Energy_Eco_Cyc());
        this.setUnixtimestamp(vcu65CanMessage.getUnixtimestamp());
        return this;
    }
}
