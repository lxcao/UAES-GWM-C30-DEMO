package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.charger.Charger;
import com.uaes.esw.gwmc30demo.domain.model.journey.Journey;

import java.util.List;

public interface IChargeTime {
    List<Charger> calChargeTimeByChargerType(Journey journey);
}
