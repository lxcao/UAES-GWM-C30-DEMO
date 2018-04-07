package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU61CanMessage {
    long unixtimestamp;
    double HVBatt_Energy_Sum_Cyc;//本次驾驶循环电池能量消耗
    double Motor_Energy_Sum_Cyc;//本次驾驶循环电机能量消耗
    double DCDC_Energy_Sum_Cyc;//本次驾驶循环DCDC能量消耗
    double PTC_Energy_Sum_Cyc;//本次驾驶循环制热空调能量消耗

    public VCU61CanMessage adding(VCU61CanMessage vcu61CanMessage){
        this.setHVBatt_Energy_Sum_Cyc(this.getHVBatt_Energy_Sum_Cyc()
                +vcu61CanMessage.getHVBatt_Energy_Sum_Cyc());
        this.setMotor_Energy_Sum_Cyc(this.getMotor_Energy_Sum_Cyc()
                +vcu61CanMessage.getMotor_Energy_Sum_Cyc());
        this.setDCDC_Energy_Sum_Cyc(this.getDCDC_Energy_Sum_Cyc()
                +vcu61CanMessage.getDCDC_Energy_Sum_Cyc());
        this.setPTC_Energy_Sum_Cyc(this.getPTC_Energy_Sum_Cyc()
                +vcu61CanMessage.getPTC_Energy_Sum_Cyc());
        this.setUnixtimestamp(vcu61CanMessage.getUnixtimestamp());
        return this;
    }

}
