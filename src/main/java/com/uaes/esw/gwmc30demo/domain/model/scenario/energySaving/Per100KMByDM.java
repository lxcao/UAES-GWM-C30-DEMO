package com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Per100KMByDM {
    double EEconomyDM;
    double EconomyDM;
    double NormalDM;
    double EPowerDM;
    double PowerDM;
    double CustomerDM;
}
