package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.B1CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.entity.can.B2CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus.*;
import com.uaes.esw.gwmc30demo.domain.repository.battery.IBatteryRepository;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;
import com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils;

import static com.uaes.esw.gwmc30demo.constant.BatteryBalanceConstants.*;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;
import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.GMW_C30_VIN_CODE;
import static com.uaes.esw.gwmc30demo.domain.repository.battery.IBatteryRepository.*;
import static com.uaes.esw.gwmc30demo.domain.repository.can.ICanRepository.getB1CanMessageFromRedis;
import static com.uaes.esw.gwmc30demo.domain.repository.can.ICanRepository.getB2CanMessageFromRedis;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.sleepSeconds;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.batteryBalanceLogInfo;

public interface BatteryDomainService {

    static BatteryStatus creteBatteryStatus(){
        B1CanMessage b1CanMessage = getB1CanMessageFromRedis();
        B2CanMessage b2CanMessage = getB2CanMessageFromRedis();
        BatteryStatus batteryStatus = BatteryStatus.builder()
                .Pack_Curr_BMS(b1CanMessage.getPack_Curr_BMS())
                .Pack_Volt_BMS(b1CanMessage.getPack_Volt_BMS())
                .Pack_Soc_BMS(b1CanMessage.getPack_Soc_BMS())
                .Pack_Temp_BMS(b1CanMessage.getPack_Temp_BMS())
                .Pack_ChrgSts_BMS(b1CanMessage.getPack_ChrgSts_BMS())
                .Pack_ChrgReTime_BMS(b2CanMessage.getPack_ChrgReTime_BMS())
                .Pack_BalcSts_BMS(b1CanMessage.getPack_BalcSts_BMS())
                .Pack_CellSocMax_BMS(b2CanMessage.getPack_CellSocMax_BMS())
                .Pack_CellSOCMin_BMS(b2CanMessage.getPack_CellSocMin_BMS())
                .Pack_ChrgReq_BMS(calChargeRequire(getBatterySnapshot(GMW_C30_VIN_CODE)))
                .build();
        return batteryStatus;
    }

    static BatteryStatusNotice createBatteryStatusNotice(){
        BatteryStatusNotice batteryStatusNotice = BatteryStatusNotice.builder()
                .batteryStatus(creteBatteryStatus())
                .dateTime(DateTimeUtils.getDateTimeString()).build();
        return batteryStatusNotice;
    }

    static BatteryBalanceNotice createBatteryBalanceNotice(){
        Battery batteryNow = getBatterySnapshot(GMW_C30_VIN_CODE);
        BatteryBalance batteryBalance = BatteryBalance.builder().build();
        batteryBalance.setBalanceEnable(calBalanceEnable(batteryNow));
        if(batteryNow.getBalanceStatus() == BATTERY_BALANCE_STATUS_ON){
            batteryBalanceLogInfo("BATTERY_BALANCE_STATUS_ON");
            batteryBalance.setBalanceLifeMileage(calLifeMileageAfterBalance(getLastBalanceStartPoint()
                    ,batteryNow));
            batteryBalance.setBalanceTime(calBalanceTime(batteryNow));
        }else if(batteryNow.getBalanceStatus() == BATTERY_BALANCE_STATUS_OFF){
            batteryBalanceLogInfo("BATTERY_BALANCE_STATUS_OFF");
            batteryBalance.setBalanceEnable(calBalanceEnable(batteryNow));
            batteryBalance.setBalanceLifeMileage(calLifeMileageAfterBalance(batteryNow
                    ,createBatteryZero()));
            batteryBalance.setBalanceTime(calBalanceTime(batteryNow));
        }else{
            batteryBalanceLogInfo("BATTERY_BALANCE_STATUS_OTHER");
            batteryBalance.setBalanceEnable(calBalanceEnable(batteryNow));
            batteryBalance.setBalanceLifeMileage(calLifeMileageAfterBalance(createBatteryZero()
                    ,createBatteryZero()));
            batteryBalance.setBalanceTime(calBalanceTime(createBatteryZero()));
        }
        BatteryBalanceNotice batteryBalanceNotice = BatteryBalanceNotice.builder()
                .batteryBalance(batteryBalance)
                .batteryStatus(creteBatteryStatus())
                .dateTime(DateTimeUtils.getDateTimeString()).build();
        return batteryBalanceNotice;
    }

    static double calSOCDeltaValue(Battery battery){
        return battery.getSocMax() - battery.getSocMin();
    }

    static int calChargeRequire(Battery battery){
        double soc = battery.getSoc();
        //System.out.println("soc="+soc);
        if(soc < BATTERY_CHARGE_REQUIRE_THRESHOLD)
            return BATTERY_CHARGE_REQUIRE;
        return BATTERY_CHARGE_NO_REQUIRE;
    }

    static double getBatterySOC(Battery battery){
        return battery.getSoc();
    }

    static double calLifeMileageAfterBalance(Battery startBattery, Battery nowBattery){
        batteryBalanceLogInfo("calLifeMileageAfterBalance:startBattery="+startBattery);
        batteryBalanceLogInfo("calLifeMileageAfterBalance:nowBattery="+nowBattery);
        return (calSOCDeltaValue(startBattery) - calSOCDeltaValue(nowBattery))/BATTERY_PARAMETER_HUNDRED
                *BATTERY_NOMINAL_CAPACITY_AH*BATTERY_NOMINAL_VOLTAGE_V/BATTERY_PARAMETER_THOUSAND
                *BATTERY_PARAMETER_HUNDRED/BATTERY_POWER_CONSUMPTION_KWH_PER_100KM;
    }

    static double calBalanceTime(Battery nowBattery){
        batteryBalanceLogInfo("calBalanceTime:nowBattery="+nowBattery);
        return calSOCDeltaValue(nowBattery)/BATTERY_PARAMETER_HUNDRED
                *BATTERY_NOMINAL_CAPACITY_AH/BATTERY_AVERAGE_BALANCE_CURRENT_A;
    }

    static int calBalanceEnable(Battery nowBattery){
        if(calSOCDeltaValue(nowBattery) > BATTERY_BALANCE_START_D_VALUE)
            return BATTERY_BALANCE_ENABLE;
        return BATTERY_BALANCE_DISABLE;
    }

    static StartBatteryBalanceRes startBBDomainService(StartBatteryBalanceReq startBatteryBalanceReq){
        BatteryBalanceInstruction batteryBalanceInstruction = BatteryBalanceInstruction.builder()
                .balanceInstruction(BATTERY_BALANCE_INSTRUCTION_START)
                .dateTime(DateTimeUtils.getDateTimeString()).build();
        IVehicleRepository.sendBatteryBalance2Vehicle(batteryBalanceInstruction);
        Battery batteryNow = getBatterySnapshot(GMW_C30_VIN_CODE);
        IBatteryRepository.storeBalanceStartPoint(batteryNow);
        //wait for 5 secs until BMS feedback
        sleepSeconds(BATTERY_BALANCE_START_WAIT_SECOND);
        BatteryBalance batteryBalance = BatteryBalance.builder()
                .balanceEnable(calBalanceEnable(batteryNow))
                .balanceLifeMileage(BATTERY_BALANCE_DOUBLE_ZERO)
                .balanceTime(calBalanceTime(batteryNow))
                .build();
        BatteryStatus batteryStatus = creteBatteryStatus();
        StartBatteryBalanceRes startBatteryBalanceRes = StartBatteryBalanceRes.builder()
                .driver(startBatteryBalanceReq.getDriver())
                .batteryBalance(batteryBalance)
                .batteryStatus(batteryStatus)
                .responseCode(RESPONSE_CODE_SUCCESS)
                .dateTime(startBatteryBalanceReq.getDateTime()).build();
        return startBatteryBalanceRes;
    }

    static StopBatteryBalanceRes stopBBDomainService(StopBatteryBalanceReq stopBatteryBalanceReq){
        BatteryBalanceInstruction batteryBalanceInstruction = BatteryBalanceInstruction.builder()
                .balanceInstruction(BATTERY_BALANCE_INSTRUCTION_STOP)
                .dateTime(DateTimeUtils.getDateTimeString()).build();
        IVehicleRepository.sendBatteryBalance2Vehicle(batteryBalanceInstruction);
        Battery batteryNow = getBatterySnapshot(GMW_C30_VIN_CODE);
        IBatteryRepository.storeBalanceStopPoint(batteryNow);
        BatteryBalance batteryBalance = BatteryBalance.builder()
                .balanceEnable(calBalanceEnable(batteryNow))
                .balanceLifeMileage(calLifeMileageAfterBalance(getLastBalanceStartPoint(),batteryNow))
                .balanceTime(BATTERY_BALANCE_DOUBLE_ZERO)
                .build();
        BatteryStatus batteryStatus = creteBatteryStatus();
        StopBatteryBalanceRes stopBatteryBalanceRes = StopBatteryBalanceRes.builder()
                .driver(stopBatteryBalanceReq.getDriver())
                .batteryBalance(batteryBalance)
                .batteryStatus(batteryStatus)
                .responseCode(RESPONSE_CODE_SUCCESS)
                .dateTime(stopBatteryBalanceReq.getDateTime()).build();
        return stopBatteryBalanceRes;
    }
}
