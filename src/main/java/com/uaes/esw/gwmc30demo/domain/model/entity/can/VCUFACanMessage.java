package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCUFACanMessage {
    long unixtimestamp;
    int DCDC_OperMod; //DCDC变压器工作状态
}
