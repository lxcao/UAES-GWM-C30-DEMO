package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU67CanMessage {
    long unixtimestamp;
    double HVBatt_Energy_Norm_Cyc;//Normal模式本次驾驶循环电池能量消耗
    double Motor_Energy_Norm_Cyc;//Normal模式本次驾驶循环电机能量消耗
    double DCDC_Energy_Norm_Cyc;//Normal模式本次驾驶循环DCDC能量消耗
    double PTC_Energy_Norm_Cyc;//Normal模式本次驾驶循环制热空调能量消耗

    public VCU67CanMessage adding(VCU67CanMessage vcu67CanMessage){
        this.setHVBatt_Energy_Norm_Cyc(this.getHVBatt_Energy_Norm_Cyc()
                +vcu67CanMessage.getHVBatt_Energy_Norm_Cyc());
        this.setMotor_Energy_Norm_Cyc(this.getMotor_Energy_Norm_Cyc()
                +vcu67CanMessage.getMotor_Energy_Norm_Cyc());
        this.setDCDC_Energy_Norm_Cyc(this.getDCDC_Energy_Norm_Cyc()
                +vcu67CanMessage.getDCDC_Energy_Norm_Cyc());
        this.setPTC_Energy_Norm_Cyc(this.getPTC_Energy_Norm_Cyc()
                +vcu67CanMessage.getPTC_Energy_Norm_Cyc());
        this.setUnixtimestamp(vcu67CanMessage.getUnixtimestamp());
        return  this;
    }
}
