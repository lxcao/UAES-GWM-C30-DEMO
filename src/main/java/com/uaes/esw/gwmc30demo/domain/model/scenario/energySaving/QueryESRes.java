package com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving;

import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class QueryESRes {
    Driver driver;
    String dateTime;
    ESSummary esSummary;//总计电量、电费、百公里电耗
    ComponentsPercentSum componentsPercentSum;//各个部件总计电耗比例
    Per100KMByDM per100KMByDM;//各个模式百公里电耗
    Map<String,ComponentsPercent> CPByDM;//各个模式中的各个部件电耗比例
    String responseCode;
}
