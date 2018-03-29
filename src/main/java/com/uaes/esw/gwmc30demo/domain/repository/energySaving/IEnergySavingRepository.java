package com.uaes.esw.gwmc30demo.domain.repository.energySaving;

import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.ESRemind;

public interface IEnergySavingRepository {
    static ESRemind getRemindFromRedis(){
        //TODO: should get from Redis
        ESRemind esRemind = ESRemind.builder()
                .motorEfficiency(0.18).ACOnOff(2).driveStatus(1).build();
        return esRemind;
    }
}
