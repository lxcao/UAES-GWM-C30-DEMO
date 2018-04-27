package com.uaes.esw.gwmc30demo.domain.service;

import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.HV_POWNER_STATUS_OFF;
import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.HV_POWNER_STATUS_ON;
import static com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository.storeLastEnergySavingCycle;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.getHVPowerOnStatusNow;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.getHVPowerOnStatusPrevious;
import static com.uaes.esw.gwmc30demo.domain.service.DrivingModeDomainService.resetDefaultDM2CurrentDMAsPowerOff4AllDriver;
import static com.uaes.esw.gwmc30demo.domain.service.EnergySavingDomainService.getAndStoreLastEnergySavingCycleAsPowerOff;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.vehicleLogInfo;

public class VehicleDomainService {
    public static boolean isHVPowerOnNow(){
        int hvPowerOnValue = getHVPowerOnStatusNow();
        return hvPowerOnValue != 0;
    }

    static String hvPowerStatus = HV_POWNER_STATUS_OFF;
    static boolean isHVPowerStatusChangeFromOn2Off(){
        int hvPowerOnNowValue = getHVPowerOnStatusNow();
        int hvPowerOnPreviousValue = getHVPowerOnStatusPrevious();
        vehicleLogInfo(hvPowerOnPreviousValue+" "+hvPowerOnNowValue+" "+hvPowerStatus);
        if(hvPowerOnPreviousValue == 1 && hvPowerOnNowValue == 1){
            if(hvPowerStatus.equals(HV_POWNER_STATUS_OFF))
                hvPowerStatus = HV_POWNER_STATUS_ON;
            return false;
        }else if(hvPowerOnPreviousValue == 0 && hvPowerOnNowValue == 1){
            hvPowerStatus = HV_POWNER_STATUS_ON;
            return false;
        }else if(hvPowerOnPreviousValue == 0 && hvPowerOnNowValue == 0){
            if(hvPowerStatus.equals(HV_POWNER_STATUS_ON)){
                hvPowerStatus = HV_POWNER_STATUS_OFF;
                return true;
            }
            return false;
        }else if(hvPowerOnPreviousValue == 1 && hvPowerOnNowValue == 0){
            hvPowerStatus = HV_POWNER_STATUS_OFF;
            return true;
        }
        return false;
    }

    public static void dealStaffWhenPowerOff(){
        if(isHVPowerStatusChangeFromOn2Off()){
            vehicleLogInfo("found isHVPowerStatusChangeFromOn2Off");
            //当断电后，记录一次驾驶循环
            getAndStoreLastEnergySavingCycleAsPowerOff();
            //当断电后，将所有司机的currentDM 重置为defaultDM
            resetDefaultDM2CurrentDMAsPowerOff4AllDriver();
        }
    }
}
