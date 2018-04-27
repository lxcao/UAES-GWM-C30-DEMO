package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU60CanMessage {
    long unixtimestamp;
    int Regen_Level; //制动能量回收等级
    int Filter_Level; //驾驶性滤波等级
    int Vmax_Level; //最高车速等级
    int Acceleration_Level; //加速性等级
    int AccessoryPower_Level; //附件功率等级
    int ModeSwitch_Request; //模式切换请求
    int Whether_Status; //天气状况
    double Air_Quality_Value; //空气质量指数
    double Environment_Temperature; //环境温度
}
