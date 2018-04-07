package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU71CanMessage {
    long unixtimestamp;
    double HVBatt_Energy_UltraSport_Cyc;//UltraSport模式本次驾驶循环电池能量消耗
    double Motor_Energy_UltraSport_Cyc;//UltraSport模式本次驾驶循环电机能量消耗
    double DCDC_Energy_UltraSport_Cyc;//UltraSport模式本次驾驶循环DCDC能量消耗
    double PTC_Energy_UltraSport_Cyc;//UltraSport模式本次驾驶循环制热空调能量消耗

    public VCU71CanMessage adding(VCU71CanMessage vcu71CanMessage){
        this.setHVBatt_Energy_UltraSport_Cyc(this.getHVBatt_Energy_UltraSport_Cyc()
                +vcu71CanMessage.getHVBatt_Energy_UltraSport_Cyc());
        this.setMotor_Energy_UltraSport_Cyc(this.getMotor_Energy_UltraSport_Cyc()
                +vcu71CanMessage.getMotor_Energy_UltraSport_Cyc());
        this.setDCDC_Energy_UltraSport_Cyc(this.getDCDC_Energy_UltraSport_Cyc()
                +vcu71CanMessage.getDCDC_Energy_UltraSport_Cyc());
        this.setPTC_Energy_UltraSport_Cyc(this.getPTC_Energy_UltraSport_Cyc()
                +vcu71CanMessage.getPTC_Energy_UltraSport_Cyc());
        this.setUnixtimestamp(vcu71CanMessage.getUnixtimestamp());
        return  this;
    }
}
