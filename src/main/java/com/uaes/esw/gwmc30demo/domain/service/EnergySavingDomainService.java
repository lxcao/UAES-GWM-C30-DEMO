package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.*;
import com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository;
import com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils;

import java.util.HashMap;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;

public interface EnergySavingDomainService {
    static QueryESRes queryESThisTimeDomainService(QueryESReq esReq){
        return testQuery(esReq);
    }

    static QueryESRes queryESTodayDomainService(QueryESReq esReq){
        return testQuery(esReq);
    }

    static QueryESRes queryESThisWeekDomainService(QueryESReq esReq){
        return testQuery(esReq);
    }

    static QueryESRes queryESCustomerDomainService(QueryESReq esReq){
        return testQuery(esReq);
    }

    static QueryESRes testQuery(QueryESReq esReq){
        ESSummary esSummary = ESSummary.builder().energyPer100KM(18.5)
                .sumEnergy(36.35).sumEnergyCost(22.5).build();
        ComponentsPercentSum componentsPercentSum = ComponentsPercentSum.builder()
                .RESTEnergyPert(0.10).ACEnergyPert(0.25).DCDCEngergyPert(0.35).MTEnergyPert(0.30).BRKEnergyPert(0.10).build();
        Per100KMByDM per100KMByDM = Per100KMByDM.builder()
                .CustomerDM(22.8).EconomyDM(41.6).NormalDM(53.5).PowerDM(76.1).EPowerDM(79.9).EEconomyDM(74.5).build();
        Map<String,ComponentsPercent> cpByDM = new HashMap<>();
        ComponentsPercent componentsPercentEEM = ComponentsPercent.builder()
                .RESTEnergyPert(0.10).ACEnergyPert(0.25).DCDCEngergyPert(0.35).MTEnergyPert(0.30).build();
        cpByDM.put("EEM",componentsPercentEEM);
        ComponentsPercent componentsPercentECO = ComponentsPercent.builder()
                .RESTEnergyPert(0.10).ACEnergyPert(0.25).DCDCEngergyPert(0.35).MTEnergyPert(0.30).build();
        cpByDM.put("ECO",componentsPercentECO);
        ComponentsPercent componentsPercentNOR = ComponentsPercent.builder()
                .RESTEnergyPert(0.10).ACEnergyPert(0.25).DCDCEngergyPert(0.35).MTEnergyPert(0.30).build();
        cpByDM.put("NOR",componentsPercentNOR);
        ComponentsPercent componentsPercentEPM = ComponentsPercent.builder()
                .RESTEnergyPert(0.10).ACEnergyPert(0.25).DCDCEngergyPert(0.35).MTEnergyPert(0.30).build();
        cpByDM.put("EPM",componentsPercentEPM);
        ComponentsPercent componentsPercentPOW = ComponentsPercent.builder()
                .RESTEnergyPert(0.10).ACEnergyPert(0.25).DCDCEngergyPert(0.35).MTEnergyPert(0.30).build();
        cpByDM.put("POW",componentsPercentPOW);
        ComponentsPercent componentsPercentCST = ComponentsPercent.builder()
                .RESTEnergyPert(0.10).ACEnergyPert(0.25).DCDCEngergyPert(0.35).MTEnergyPert(0.30).build();
        cpByDM.put("CST",componentsPercentCST);
        QueryESRes queryESRes = QueryESRes.builder()
                .driver(esReq.getDriver())
                .dateTime(esReq.getDateTime())
                .esSummary(esSummary)
                .componentsPercentSum(componentsPercentSum)
                .per100KMByDM(per100KMByDM)
                .CPByDM(cpByDM)
                .responseCode(RESPONSE_CODE_SUCCESS)
                .build();
        return queryESRes;
    }


    static ESRemindNotice createESRemind(){
        ESRemind esRemind = IEnergySavingRepository.getRemindFromRedis();
        ESRemindNotice esRemindNotice = ESRemindNotice.builder()
                .esRemind(esRemind).dateTime(DateTimeUtils.getDateTimeString()).build();
        return esRemindNotice;
    }

}
