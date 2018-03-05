package com.uaes.esw.gwmc30demo.application.service;

import com.uaes.esw.gwmc30demo.domain.model.charger.Charger;
import com.uaes.esw.gwmc30demo.domain.model.journey.Journey;

import java.util.List;

public interface JourneyService {
    //得到规划的里程
    List<Journey> getJourney();
    //返回规划里程的充电时间
    List<List<Charger>> feedbackChargeTimeByChargerType();
}
