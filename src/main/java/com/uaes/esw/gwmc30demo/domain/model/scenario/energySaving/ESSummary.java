package com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ESSummary {
    double sumEnergy;//总电量
    double sumEnergyCost;//总电费
    double energyPer100KM;//总百公里电耗

}
