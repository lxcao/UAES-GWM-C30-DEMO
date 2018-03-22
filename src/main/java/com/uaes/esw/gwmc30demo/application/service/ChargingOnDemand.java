package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.JourneyService.chargingOnDemandByJourney;

public interface ChargingOnDemand{

    static String chargingOnDemandByJourneyAssembler(String journeyString){
        //TODO: should be parse by gson to get the vinCode from journeyString
        return chargingOnDemandByJourney("111111", journeyString);
    }



}
