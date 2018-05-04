package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.Vehicle;
import com.uaes.esw.gwmc30demo.domain.model.scenario.vehicleStatus.VehicleStatus;
import com.uaes.esw.gwmc30demo.domain.model.scenario.vehicleStatus.VehicleStatusNotice;
import com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils;

import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.*;
import static com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository.storeLastEnergySavingCycle;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.getHVPowerOnStatusNow;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.getHVPowerOnStatusPrevious;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.getVehicleSnapshot;
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
        //vehicleLogInfo(hvPowerOnPreviousValue+" "+hvPowerOnNowValue+" "+hvPowerStatus);
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

    static int getACOperationMode(Vehicle vehicle){
        double ptcPCnsmptn = vehicle.getBattery().getPtcPCnsmptn();
        double cmpPCnsmptn = vehicle.getBattery().getCmpPCnsmptn();
        if(ptcPCnsmptn > AC_POWER_CONSUMPTION_THRESHOLD)
            return AC_STATUS_PTC;
        else if(cmpPCnsmptn > AC_POWER_CONSUMPTION_THRESHOLD)
            return AC_STATUS_CMP;
        return AC_STATUS_NO;
    }

    public static VehicleStatusNotice createVehicleStatusNotice(){
        Vehicle vehicle = getVehicleSnapshot(GMW_C30_VIN_CODE);
        //vehicleLogInfo("vehicleSnapShot="+vehicle);
        VehicleStatus vehicleStatus = VehicleStatus.builder()
                .DCDC_OperMod(vehicle.getBattery().getDcdcOperMod())
                .HV_PowerOn(vehicle.getBattery().getHvPower())
                .Pack_ChrgReTime_BMS(vehicle.getBattery().getChargingTime())
                .Pack_ChrgSts_BMS(vehicle.getBattery().getChargingStatus())
                .VCU_RemainDistance(vehicle.getBattery().getRemainDistance())
                .TM_OperMod(vehicle.getBattery().getTmOperMod())
                .AC_OperMod(getACOperationMode(vehicle))
                .build();
        //vehicleLogInfo("vehicleStatus="+vehicleStatus);
        VehicleStatusNotice vehicleStatusNotice = VehicleStatusNotice.builder()
                .vehicleStatus(vehicleStatus)
                .dateTime(DateTimeUtils.getDateTimeString()).build();
        //vehicleLogInfo("vehicleStatusNotice="+vehicleStatusNotice);
        return vehicleStatusNotice;
    }
}
