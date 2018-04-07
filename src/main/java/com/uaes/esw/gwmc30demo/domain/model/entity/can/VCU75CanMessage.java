package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU75CanMessage {
    long unixtimestamp;
    double HVBatt_Energy_Custom_Cyc;//自定义模式本次驾驶循环电池能量消耗
    double Motor_Energy_Custom_Cyc;//自定义模式本次驾驶循环电机能量消耗
    double DCDC_Energy_Custom_Cyc;//自定义模式本次驾驶循环DCDC能量消耗
    double PTC_Energy_Custom_Cyc;//自定义模式本次驾驶循环制热空调能量消耗

    public VCU75CanMessage adding(VCU75CanMessage vcu75CanMessage){
        this.setHVBatt_Energy_Custom_Cyc(this.getHVBatt_Energy_Custom_Cyc()
                +vcu75CanMessage.getHVBatt_Energy_Custom_Cyc());
        this.setMotor_Energy_Custom_Cyc(this.getMotor_Energy_Custom_Cyc()
                +vcu75CanMessage.getMotor_Energy_Custom_Cyc());
        this.setDCDC_Energy_Custom_Cyc(this.getDCDC_Energy_Custom_Cyc()
                +vcu75CanMessage.getDCDC_Energy_Custom_Cyc());
        this.setPTC_Energy_Custom_Cyc(this.getPTC_Energy_Custom_Cyc()
                +vcu75CanMessage.getPTC_Energy_Custom_Cyc());
        this.setUnixtimestamp(vcu75CanMessage.getUnixtimestamp());
        return  this;
    }
}
