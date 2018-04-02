package com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ESRemindNotice {
    ESRemind esRemind;
    String dateTime;

}
