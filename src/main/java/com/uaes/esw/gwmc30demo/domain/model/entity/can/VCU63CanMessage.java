package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU63CanMessage {
    long unixtimestamp;
    double HVBatt_Energy_UltraEco_Cyc;//UltraEco模式本次驾驶循环电池能量消耗
    double Motor_Energy_UltraEco_Cyc;//UltraEco模式本次驾驶循环电机能量消耗
    double DCDC_Energy_UltraEco_Cyc;//UltraEco模式本次驾驶循环DCDC能量消耗
    double PTC_Energy_UltraEco_Cyc;//UltraEco模式本次驾驶循环制热空调能量消耗

    public VCU63CanMessage adding(VCU63CanMessage vcu63CanMessage){
        this.setHVBatt_Energy_UltraEco_Cyc(this.getHVBatt_Energy_UltraEco_Cyc()
                +vcu63CanMessage.getHVBatt_Energy_UltraEco_Cyc());
        this.setMotor_Energy_UltraEco_Cyc(this.getMotor_Energy_UltraEco_Cyc()
                +vcu63CanMessage.getMotor_Energy_UltraEco_Cyc());
        this.setDCDC_Energy_UltraEco_Cyc(this.getDCDC_Energy_UltraEco_Cyc()
                +vcu63CanMessage.getDCDC_Energy_UltraEco_Cyc());
        this.setPTC_Energy_UltraEco_Cyc(this.getPTC_Energy_UltraEco_Cyc()
                +vcu63CanMessage.getPTC_Energy_UltraEco_Cyc());
        this.setUnixtimestamp(vcu63CanMessage.getUnixtimestamp());
        return this;
    }
}
