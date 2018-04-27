package com.uaes.esw.gwmc30demo.domain.model.entity.vehicle;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DrivingModeConfigure {
    private int maxSpeed; //最高车速等级
    private int energyRecovery; //制动能量回收等级
    private int powerCorresponding; //加速性等级
    private int smoothness; //驾驶性滤波等级
    private int accessoryPerformance; //附件功率等级
}
