package com.uaes.esw.gwmc30demo.domain.model.entity.can;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class B3CanMessage {
    long unixtimestamp;
    int Cus_BalcReq; //用户均衡请求指令
}
