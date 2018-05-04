package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU294CanMessage {
    long unixtimestamp;
    double VCU_RemainDistance; // 剩余续航里程
}
