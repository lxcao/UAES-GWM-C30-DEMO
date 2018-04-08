package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU73CanMessage {
    long unixtimestamp;
    double Motor_Efficiency;//电机效率
    int AC_On_Off_Remind;//空调开关提醒
    int Fierce_Drive_Status;//激烈驾驶状态
    int HV_PowerOn;//高压上电标志
}
