package com.uaes.esw.gwmc30demo.domain.repository.charger;

import com.uaes.esw.gwmc30demo.domain.model.entity.charger.Charger;

import java.util.ArrayList;
import java.util.List;

import static com.uaes.esw.gwmc30demo.constant.ChargerConstants.*;
import static com.uaes.esw.gwmc30demo.constant.ChargerConstants.CHARGER_DC_POWER_KW;

public interface IChargerRepository {
    static List<Charger> getChargerList(){
        List<Charger> chargerList = new ArrayList<>();
        Charger ACCharger = Charger.builder().type(CHARGER_AC_POWER_TYPE)
                .chargingPower(CHARGER_AC_POWER_KW)
                .build();
        chargerList.add(ACCharger);
        Charger DCCharger = Charger.builder().type(CHARGER_DC_POWER_TYPE)
                .chargingPower(CHARGER_DC_POWER_KW)
                .build();
        chargerList.add(DCCharger);

        return chargerList;
    }
}
