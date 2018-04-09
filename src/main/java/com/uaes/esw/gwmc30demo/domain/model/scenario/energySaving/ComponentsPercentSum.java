package com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ComponentsPercentSum {
    double ACEnergyPert;//空调百分比
    double MTEnergyPert;//电机百分比
    double DCDCEngergyPert;//变压器百分比
    double RESTEnergyPert;//其他百分比
    double BRKEnergyPert; //摩擦制动百分比
}
