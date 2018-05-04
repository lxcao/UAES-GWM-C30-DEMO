package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCUFBCanMessage {
    long unixtimestamp;
    int TM_OperMod; //电机工作状态
}
