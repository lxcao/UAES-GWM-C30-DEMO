package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.*;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus.*;
import com.uaes.esw.gwmc30demo.domain.repository.battery.IBatteryRepository;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;
import com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

import static com.uaes.esw.gwmc30demo.constant.BatteryBalanceConstants.*;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;
import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.GMW_C30_VIN_CODE;
import static com.uaes.esw.gwmc30demo.domain.repository.battery.IBatteryRepository.*;
import static com.uaes.esw.gwmc30demo.domain.repository.can.ICanRepository.*;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.sleepSeconds;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.batteryBalanceLogInfo;

public interface BatteryDomainService {

    static BatteryStatus creteBatteryStatus(){
        B1CanMessage b1CanMessage = getLastBMSB1CanMessageFromRedis();
        B2CanMessage b2CanMessage = getLastBMSB2CanMessageFromRedis();
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

    static CSCVCellNotice createCSCVCellNotice(){
        List<Double> cellVoltageList = new ArrayList<>();
        //Cell01,02
        BMS432CanMessage bms432CanMessage = getLastBMS432MessageFromRedis();
        cellVoltageList.add(0, bms432CanMessage.getCSC_03_Vcell_08());
        cellVoltageList.add(1, bms432CanMessage.getCSC_03_Vcell_09());
        //Cell03,04,05
        BMS442CanMessage bms442CanMessage = getLastBMS442MessageFromRedis();
        cellVoltageList.add(2, bms442CanMessage.getCSC_03_Vcell_10());
        cellVoltageList.add(3, bms442CanMessage.getCSC_03_Vcell_11());
        cellVoltageList.add(4, bms442CanMessage.getCSC_03_Vcell_12());
        //Cell06
        BMS452CanMessage bms452CanMessage = getLastBMS452MessageFromRedis();
        cellVoltageList.add(5, bms452CanMessage.getCSC_03_Vcell_13());
        //Cell07,08,09
        BMS412CanMessage bms412CanMessage = getLastBMS412MessageFromRedis();
        cellVoltageList.add(6,bms412CanMessage.getCSC_03_Vcell_01());
        cellVoltageList.add(7,bms412CanMessage.getCSC_03_Vcell_02());
        cellVoltageList.add(8,bms412CanMessage.getCSC_03_Vcell_03());
        //Cell10,11,12
        BMS422CanMessage bms422CanMessage = getLastBMS422MessageFromRedis();
        cellVoltageList.add(9,bms422CanMessage.getCSC_03_Vcell_04());
        cellVoltageList.add(10,bms422CanMessage.getCSC_03_Vcell_05());
        cellVoltageList.add(11,bms422CanMessage.getCSC_03_Vcell_06());
        //Cell13,14,15
        BMS418CanMessage bms418CanMessage = getLastBMS418MessageFromRedis();
        cellVoltageList.add(12, bms418CanMessage.getCSC_05_Vcell_01());
        cellVoltageList.add(13, bms418CanMessage.getCSC_05_Vcell_02());
        cellVoltageList.add(14, bms418CanMessage.getCSC_05_Vcell_03());
        //Cell16,17,18
        BMS428CanMessage bms428CanMessage = getLastBMS428MessageFromRedis();
        cellVoltageList.add(15,bms428CanMessage.getCSC_05_Vcell_04());
        cellVoltageList.add(16,bms428CanMessage.getCSC_05_Vcell_05());
        cellVoltageList.add(17,bms428CanMessage.getCSC_05_Vcell_06());
        //Cell19,20,21
        BMS431CanMessage bms431CanMessage = getLastBMS431MessageFromRedis();
        cellVoltageList.add(18, bms431CanMessage.getCSC_02_Vcell_07());
        cellVoltageList.add(19, bms431CanMessage.getCSC_02_Vcell_08());
        cellVoltageList.add(20, bms431CanMessage.getCSC_02_Vcell_09());
        //Cell22,23
        BMS449CanMessage bms449CanMessage = getLastBMS449MessageFromRedis();
        cellVoltageList.add(21,bms449CanMessage.getCSC_02_Vcell_10());
        cellVoltageList.add(22,bms449CanMessage.getCSC_02_Vcell_11());
        //Cell24,25,26
        BMS411CanMessage bms411CanMessage = getLastBMS411MessageFromRedis();
        cellVoltageList.add(23, bms411CanMessage.getCSC_02_Vcell_01());
        cellVoltageList.add(24, bms411CanMessage.getCSC_02_Vcell_02());
        cellVoltageList.add(25, bms411CanMessage.getCSC_02_Vcell_03());
        //Cell27,28
        BMS421CanMessage bms421CanMessage = getLastBMS421MessageFromRedis();
        cellVoltageList.add(26,bms421CanMessage.getCSC_02_Vcell_04());
        cellVoltageList.add(27, bms421CanMessage.getCSC_02_Vcell_05());
        //Cell29,30,31
        BMS430CanMessage bms430CanMessage = getLastBMS430MessageFromRedis();
        cellVoltageList.add(28, bms430CanMessage.getCSC_01_Vcell_07());
        cellVoltageList.add(29, bms430CanMessage.getCSC_01_Vcell_08());
        cellVoltageList.add(30, bms430CanMessage.getCSC_01_Vcell_09());
        //Cell32,33
        BMS440CanMessage bms440CanMessage = getLastBMS440MessageFromRedis();
        cellVoltageList.add(31, bms440CanMessage.getCSC_01_Vcell_10());
        cellVoltageList.add(32, bms440CanMessage.getCSC_01_Vcell_11());
        //Cell34,35,36
        BMS410CanMessage bms410CanMessage = getLastBMS410MessageFromRedis();
        cellVoltageList.add(33, bms410CanMessage.getCSC_01_Vcell_01());
        cellVoltageList.add(34, bms410CanMessage.getCSC_01_Vcell_02());
        cellVoltageList.add(35, bms410CanMessage.getCSC_01_Vcell_03());
        //Cell37,38
        BMS420CanMessage bms420CanMessage = getLastBMS420MessageFromRedis();
        cellVoltageList.add(36, bms420CanMessage.getCSC_01_Vcell_04());
        cellVoltageList.add(37, bms420CanMessage.getCSC_01_Vcell_05());
        //Cell39,40,41
        BMS433CanMessage bms433CanMessage = getLastBMS433MessageFromRedis();
        cellVoltageList.add(38, bms433CanMessage.getCSC_04_Vcell_07());
        cellVoltageList.add(39, bms433CanMessage.getCSC_04_Vcell_08());
        cellVoltageList.add(40, bms433CanMessage.getCSC_04_Vcell_09());
        //Cell42,43
        BMS443CanMessage bms443CanMessage = getLastBMS443MessageFromRedis();
        cellVoltageList.add(41, bms443CanMessage.getCSC_04_Vcell_10());
        cellVoltageList.add(42, bms443CanMessage.getCSC_04_vcell_11());
        //Cell44,45,46
        BMS413CanMessage bms413CanMessage = getLastBMS413MessageFromRedis();
        cellVoltageList.add(43, bms413CanMessage.getCSC_04_Vcell_01());
        cellVoltageList.add(44, bms413CanMessage.getCSC_04_Vcell_02());
        cellVoltageList.add(45, bms413CanMessage.getCSC_04_Vcell_03());
        //Cell47,48
        BMS423CanMessage bms423CanMessage = getLastBMS423MessageFromRedis();
        cellVoltageList.add(46, bms423CanMessage.getCSC_04_Vcell_04());
        cellVoltageList.add(47, bms423CanMessage.getCSC_04_Vcell_05());
        //Cell49,50,51
        BMS437CanMessage bms437CanMessage = getLastBMS437MessageFromRedis();
        cellVoltageList.add(48, bms437CanMessage.getCSC_09_Vcell_07());
        cellVoltageList.add(49, bms437CanMessage.getCSC_09_Vcell_08());
        cellVoltageList.add(50, bms437CanMessage.getCSC_09_Vcell_09());
        //Cell52,53
        BMS447CanMessage bms447CanMessage = getLastBMS447MessageFromRedis();
        cellVoltageList.add(51, bms447CanMessage.getCSC_09_Vcell_10());
        cellVoltageList.add(52, bms447CanMessage.getCSC_09_Vcell_11());
        //Cell54,55,56
        BMS417CanMessage bms417CanMessage = getLastBMS417MessageFromRedis();
        cellVoltageList.add(53, bms417CanMessage.getCSC_09_Vcell_01());
        cellVoltageList.add(54, bms417CanMessage.getCSC_09_Vcell_02());
        cellVoltageList.add(55, bms417CanMessage.getCSC_09_Vcell_03());
        //Cell57,58
        BMS427CanMessage bms427CanMessage = getLastBMS427MessageFromRedis();
        cellVoltageList.add(56, bms427CanMessage.getCSC_09_Vcell_04());
        cellVoltageList.add(57, bms427CanMessage.getCSC_09_Vcell_05());
        //Cell59,60,61
        BMS436CanMessage bms436CanMessage = getLastBMS436MessageFromRedis();
        cellVoltageList.add(58, bms436CanMessage.getCSC_08_Vcell_07());
        cellVoltageList.add(59, bms436CanMessage.getCSC_08_Vcell_08());
        cellVoltageList.add(60, bms436CanMessage.getCSC_08_Vcell_09());
        //Cell62,63
        BMS446CanMessage bms446CanMessage = getLastBMS446MessageFromRedis();
        cellVoltageList.add(61, bms446CanMessage.getCSC_08_Vcell_10());
        cellVoltageList.add(62, bms446CanMessage.getCSC_08_Vcell_11());
        //Cell64,65,66
        BMS416CanMessage bms416CanMessage = getLastBMS416MessageFromRedis();
        cellVoltageList.add(63, bms416CanMessage.getCSC_08_Vcell_01());
        cellVoltageList.add(64, bms416CanMessage.getCSC_08_Vcell_02());
        cellVoltageList.add(65, bms416CanMessage.getCSC_08_Vcell_03());
        //Cell67,68
        BMS426CanMessage bms426CanMessage = getLastBMS426MessageFromRedis();
        cellVoltageList.add(66, bms426CanMessage.getCSC_08_Vcell_04());
        cellVoltageList.add(67, bms426CanMessage.getCSC_08_Vcell_05());
        //Cell69,70
        BMS435CanMessage bms435CanMessage = getLastBMS435MessageFromRedis();
        cellVoltageList.add(68, bms435CanMessage.getCSC_07_Vcell_08());
        cellVoltageList.add(69, bms435CanMessage.getCSC_07_Vcell_09());
        //Cell71,72,73
        BMS445CanMessage bms445CanMessage = getLastBMS445MessageFromRedis();
        cellVoltageList.add(70, bms445CanMessage.getCSC_07_Vcell_10());
        cellVoltageList.add(71, bms445CanMessage.getCSC_07_Vcell_11());
        cellVoltageList.add(72, bms445CanMessage.getCSC_07_Vcell_12());
        //Cell74,75,76
        BMS415CanMessage bms415CanMessage = getLastBMS415MessageFromRedis();
        cellVoltageList.add(73, bms415CanMessage.getCSC_07_Vcell_01());
        cellVoltageList.add(74, bms415CanMessage.getCSC_07_Vcell_02());
        cellVoltageList.add(75, bms415CanMessage.getCSC_07_Vcell_03());
        //Cell77,78,79
        BMS425CanMessage bms425CanMessage = getLastBMS425MessageFromRedis();
        cellVoltageList.add(76, bms425CanMessage.getCSC_07_Vcell_04());
        cellVoltageList.add(77, bms425CanMessage.getCSC_07_Vcell_05());
        cellVoltageList.add(78, bms425CanMessage.getCSC_07_Vcell_06());
        //Cell80,81
        BMS434CanMessage bms434CanMessage = getLastBMS434MessageFromRedis();
        cellVoltageList.add(79, bms434CanMessage.getCSC_06_Vcell_08());
        cellVoltageList.add(80, bms434CanMessage.getCSC_06_Vcell_09());
        //Cell82,83,84
        BMS444CanMessage bms444CanMessage = getLastBMS444MessageFromRedis();
        cellVoltageList.add(81, bms444CanMessage.getCSC_06_Vcell_10());
        cellVoltageList.add(82, bms444CanMessage.getCSC_06_Vcell_11());
        cellVoltageList.add(83, bms444CanMessage.getCSC_06_Vcell_12());
        //Cell85
        BMS454CanMessage bms454CanMessage = getLastBMS454MessageFromRedis();
        cellVoltageList.add(84, bms454CanMessage.getCSC_06_Vcell_13());
        //Cell86,87,88
        BMS414CanMessage bms414CanMessage = getLastBMS414MessageFromRedis();
        cellVoltageList.add(85, bms414CanMessage.getCSC_06_Vcell_01());
        cellVoltageList.add(86, bms414CanMessage.getCSC_06_Vcell_02());
        cellVoltageList.add(87, bms414CanMessage.getCSC_06_Vcell_03());
        //Cell89,90,91
        BMS424CanMessage bms424CanMessage = getLastBMS424MessageFromRedis();
        cellVoltageList.add(88, bms424CanMessage.getCSC_06_Vcell_04());
        cellVoltageList.add(89, bms424CanMessage.getCSC_06_Vcell_05());
        cellVoltageList.add(90, bms424CanMessage.getCSC_06_Vcell_06());

        CSCVCellNotice cscvCellNotice = CSCVCellNotice.builder()
                .cellVoltage(cellVoltageList)
                .dateTime(DateTimeUtils.getDateTimeString()).build();
        return cscvCellNotice;
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
