package com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving;
import com.uaes.esw.gwmc30demo.domain.model.entity.can.*;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class EnergySavingCanMessage {
    VCU61CanMessage vcu61CanMessage;
    VCU62CanMessage vcu62CanMessage;
    VCU63CanMessage vcu63CanMessage;
    VCU64CanMessage vcu64CanMessage;
    VCU65CanMessage vcu65CanMessage;
    VCU66CanMessage vcu66CanMessage;
    VCU67CanMessage vcu67CanMessage;
    VCU68CanMessage vcu68CanMessage;
    VCU69CanMessage vcu69CanMessage;
    VCU70CanMessage vcu70CanMessage;
    VCU71CanMessage vcu71CanMessage;
    VCU72CanMessage vcu72CanMessage;
    VCU75CanMessage vcu75CanMessage;
    VCU76CanMessage vcu76CanMessage;
    long timestamp;
}
