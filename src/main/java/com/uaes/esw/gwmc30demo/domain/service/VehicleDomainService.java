package com.uaes.esw.gwmc30demo.domain.service;

import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.getHVPowerOnStatusNow;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.getHVPowerOnStatusPrevious;

public interface VehicleDomainService {
    static boolean isHVPowerOnNow(){
        int hvPowerOnValue = getHVPowerOnStatusNow();
        return hvPowerOnValue != 0;
    }

    static boolean isHVPowerStatusChangeFromOn2Off(){
        int hvPowerOnNowValue = getHVPowerOnStatusNow();
        int hvPowerOnPreviousValue = getHVPowerOnStatusPrevious();
        return hvPowerOnPreviousValue == 1 && hvPowerOnNowValue == 0;
    }
}
