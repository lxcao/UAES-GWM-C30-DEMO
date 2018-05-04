package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VCU29DCanMessage {
    long unixtimestamp;
    double PTC_PowerCnsmptn; // 空调制热
    double CMP_PowerCnsmptn; // 空调制冷
}
