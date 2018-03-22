package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.JourneyService.chargingOnDemandByJourney;
import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.GMW_C30_VIN_CODE;

public interface ChargingOnDemand{

    static String chargingOnDemandByJourneyAssembler(String journeyString){
        //TODO: should be parse by gson to get the vinCode from journeyString
        return chargingOnDemandByJourney(GMW_C30_VIN_CODE, journeyString);
    }



}
