/*
 * Copyright (c) 2004- 2018 UAES-ESW
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.VCU79CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox.DrivingCycle;
import com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox.FltGrdSum;
import com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox.QueryDCBDReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox.QueryDCBDRes;
import com.uaes.esw.gwmc30demo.domain.repository.can.ICanRepository;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.*;
import static java.util.stream.Collectors.groupingBy;

public class BlackBoxDomainService {
    static public QueryDCBDRes queryDCBDDomainService(QueryDCBDReq queryDCBDReq){
        Set<DrivingCycle> drivingCycleSet = queryDCByOneDate(queryDCBDReq.getQueryDate());
        return QueryDCBDRes.builder()
                .driver(queryDCBDReq.getDriver()).dateTime(queryDCBDReq.getDateTime())
                .queryDate(queryDCBDReq.getQueryDate())
                .drivingCycles(drivingCycleSet)
                .drivingCycleFrq(queryDCFrqByOneDate(drivingCycleSet))
                .responseCode(RESPONSE_CODE_SUCCESS)
                .build();
    }

    static Set<DrivingCycle> queryDCByOneDate(String queryDate){
        String queryDateStart = queryDate + SPACE + TIME_BEGIN;
        String queryDateEnd = queryDate + SPACE + TIME_END;
        Set<DrivingCycle> drivingCycleSet = IVehicleRepository.
                getDrivingCycleByPeriod(queryDateStart, queryDateEnd);
        //获取每一个驾驶循环的故障统计
        drivingCycleSet.forEach(dc -> {
            dc.setFltGrdSum(calSumFaultGradeSummaryByDrivingCycle(dc.getPowerOnTimestamp(),
                    dc.getPowerOffTimestamp()));
        });
        return drivingCycleSet;
    }

    static  int fltGrdLvl1Num = 0, fltGrdLvl2Num = 0, fltGrdLvl3Num = 0;
    static void calFltGrdSum(Stream<Integer> intStream){
        intStream.forEach(v -> {
            if(v == 1) fltGrdLvl1Num ++;
            else if (v == 2) fltGrdLvl2Num ++;
            else if(v == 3) fltGrdLvl3Num ++;
        });
    }

    //是否有故障发生
    static boolean isFaultHappened(Set<VCU79CanMessage> vcu79CanMessagesSet){
        if(vcu79CanMessagesSet.parallelStream()
                .filter(v -> v.getAccident_Flg() == 1).findAny().isPresent())
            return true;
        return false;
    }

    //加速踏板故障发生
    static boolean isAccPdlFltHappened(Set<VCU79CanMessage> vcu79CanMessagesSet){
        if(vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getAcc_Pdl_Flt_Grd() != 0).findAny().isPresent())
            return true;
        return false;
    }
    //加速踏板故障发生的等级
    static Stream<Integer> getAccPdlFltGrd(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getAcc_Pdl_Flt_Grd() != 0)
                .mapToInt(v -> v.getAcc_Pdl_Flt_Grd()).distinct().boxed();
    }
    //加速踏板故障发生的等级及其实例映射
    static public Map<Integer, List<VCU79CanMessage>> getAccPdlFltGrdAndList(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.stream()
                .filter(s -> s.getAcc_Pdl_Flt_Grd() != 0)
                .collect(groupingBy(VCU79CanMessage::getAcc_Pdl_Flt_Grd, Collectors.toList()));
    }

    //制动踏板故障发生
    static boolean isBrkPdlFltHappened(Set<VCU79CanMessage> vcu79CanMessagesSet){
        if(vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getBrk_Pdl_Flt_Grd() != 0).findAny().isPresent())
            return true;
        return false;
    }
    //制动踏板故障发生的等级
    static Stream<Integer> getBrkPdlFltGrd(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getBrk_Pdl_Flt_Grd() != 0)
                .mapToInt(v -> v.getBrk_Pdl_Flt_Grd()).distinct().boxed();
    }
    //制动踏板故障发生的等级及其实例映射
    static Map<Integer, List<VCU79CanMessage>> getBrkPdlFltGrdAndList(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.stream()
                .filter(s -> s.getBrk_Pdl_Flt_Grd() != 0)
                .collect(groupingBy(VCU79CanMessage::getBrk_Pdl_Flt_Grd, Collectors.toList()));
    }

    //档位故障发生
    static boolean isGearFltHappened(Set<VCU79CanMessage> vcu79CanMessagesSet){
        if(vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getGear_Flt_Grd() != 0).findAny().isPresent())
            return true;
        return false;
    }
    //档位故障发生的等级
    static Stream<Integer> getGearPdlFltGrd(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getGear_Flt_Grd() != 0)
                .mapToInt(v -> v.getGear_Flt_Grd()).distinct().boxed();
    }
    //档位故障发生的等级及其实例映射
    static Map<Integer, List<VCU79CanMessage>> getGearFltGrdAndList(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.stream()
                .filter(s -> s.getGear_Flt_Grd() != 0)
                .collect(groupingBy(VCU79CanMessage::getGear_Flt_Grd, Collectors.toList()));
    }

    //车速故障发生
    static boolean isSpdFltHappened(Set<VCU79CanMessage> vcu79CanMessagesSet){
        if(vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getSpd_Flt_Grd() != 0).findAny().isPresent())
            return true;
        return false;
    }
    //车速故障发生的等级
    static Stream<Integer> getSpdFltGrd(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getSpd_Flt_Grd() != 0)
                .mapToInt(v -> v.getSpd_Flt_Grd()).distinct().boxed();
    }
    //车速故障发生的等级及其实例映射
    static Map<Integer, List<VCU79CanMessage>> getSpdFltGrdAndList(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.stream()
                .filter(s -> s.getSpd_Flt_Grd() != 0)
                .collect(groupingBy(VCU79CanMessage::getSpd_Flt_Grd, Collectors.toList()));
    }

    //驱动电机故障发生
    static boolean isDrvMtrFltHappened(Set<VCU79CanMessage> vcu79CanMessagesSet){
        if(vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getDrv_Mtr_Flt_Grd() != 0).findAny().isPresent())
            return true;
        return false;
    }
    //驱动电机故障发生的等级
    static Stream<Integer> getDrvMtrFltGrd(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getDrv_Mtr_Flt_Grd() != 0)
                .mapToInt(v -> v.getDrv_Mtr_Flt_Grd()).distinct().boxed();
    }
    //驱动电机故障发生的等级及其实例映射
    static Map<Integer, List<VCU79CanMessage>> getDrvMtrFltGrdAndList(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.stream()
                .filter(s -> s.getDrv_Mtr_Flt_Grd() != 0)
                .collect(groupingBy(VCU79CanMessage::getDrv_Mtr_Flt_Grd, Collectors.toList()));
    }

    //DCDC故障发生
    static boolean isDCDCFltHappened(Set<VCU79CanMessage> vcu79CanMessagesSet){
        if(vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getDCDC_Flt_Grd() != 0).findAny().isPresent())
            return true;
        return false;
    }
    //DCDC故障发生的等级
    static Stream<Integer> getDCDCFltGrd(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getDCDC_Flt_Grd() != 0)
                .mapToInt(v -> v.getDCDC_Flt_Grd()).distinct().boxed();
    }
    //DCDC故障发生的等级及其实例映射
    static Map<Integer, List<VCU79CanMessage>> getDCDCFltGrdAndList(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.stream()
                .filter(s -> s.getDCDC_Flt_Grd() != 0)
                .collect(groupingBy(VCU79CanMessage::getDCDC_Flt_Grd, Collectors.toList()));
    }

    //电池故障发生
    static boolean isBtyFltHappened(Set<VCU79CanMessage> vcu79CanMessagesSet){
        if(vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getBty_Flt_Grd() != 0).findAny().isPresent())
            return true;
        return false;
    }
    //电池故障发生的等级
    static Stream<Integer> getBtyFltGrd(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.parallelStream()
                .filter(s -> s.getBty_Flt_Grd() != 0)
                .mapToInt(v -> v.getBty_Flt_Grd()).distinct().boxed();
    }
    //电池故障发生的等级及其实例映射
    static Map<Integer, List<VCU79CanMessage>> getBtyFltGrdAndList(Set<VCU79CanMessage> vcu79CanMessagesSet){
        return vcu79CanMessagesSet.stream()
                .filter(s -> s.getBty_Flt_Grd() != 0)
                .collect(groupingBy(VCU79CanMessage::getBty_Flt_Grd, Collectors.toList()));
    }

    static FltGrdSum calSumFaultGradeSummaryByDrivingCycle(long dcStartTime, long dcEndTime){
        Set<VCU79CanMessage> vcu79CanMessagesSet = ICanRepository.
                getVCU79MessageFromRedisByPeriod(dcStartTime, dcEndTime);
        // 初始化
        FltGrdSum fltGrdSum = FltGrdSum.builder()
                .HasFlt(false).Level1Number(0).Level2Number(0).Level3Number(0).build();
        Stream<Integer> accPdlFltGrdArray = null, brkPdlFltGrdArray = null, gearFltGrdArray = null, spdFltGrdArray = null;
        Stream<Integer> drvMtrFltGrdArray = null, dCDCFltGrdArray = null, btyFltGrdArray = null;
        //判断是否有故障
        if(isFaultHappened(vcu79CanMessagesSet)){
            fltGrdSum.setHasFlt(true);
            //加速踏板故障等级
            if(isAccPdlFltHappened(vcu79CanMessagesSet))
                accPdlFltGrdArray = getAccPdlFltGrd(vcu79CanMessagesSet);
            //制动踏板故障等级
            if(isBrkPdlFltHappened(vcu79CanMessagesSet))
                brkPdlFltGrdArray = getBrkPdlFltGrd(vcu79CanMessagesSet);
            //档位故障等级
            if(isGearFltHappened(vcu79CanMessagesSet))
                gearFltGrdArray = getGearPdlFltGrd(vcu79CanMessagesSet);
            //车速故障等级
            if(isSpdFltHappened(vcu79CanMessagesSet))
                spdFltGrdArray = getSpdFltGrd(vcu79CanMessagesSet);
            //驱动电机故障等级
            if(isDrvMtrFltHappened(vcu79CanMessagesSet))
                drvMtrFltGrdArray = getDrvMtrFltGrd(vcu79CanMessagesSet);
            //DCDC故障等级
            if(isDCDCFltHappened(vcu79CanMessagesSet))
                dCDCFltGrdArray = getDCDCFltGrd(vcu79CanMessagesSet);
            //电池故障等级
            if(isBtyFltHappened(vcu79CanMessagesSet))
                btyFltGrdArray = getBtyFltGrd(vcu79CanMessagesSet);
           //汇总故障等级1，2，3
           fltGrdLvl1Num = 0; fltGrdLvl2Num = 0; fltGrdLvl3Num = 0;
            calFltGrdSum(accPdlFltGrdArray);
            calFltGrdSum(brkPdlFltGrdArray);
            calFltGrdSum(gearFltGrdArray);
            calFltGrdSum(spdFltGrdArray);
            calFltGrdSum(drvMtrFltGrdArray);
            calFltGrdSum(dCDCFltGrdArray);
            calFltGrdSum(btyFltGrdArray);
            //设置
            fltGrdSum.setLevel1Number(fltGrdLvl1Num);
            fltGrdSum.setLevel2Number(fltGrdLvl2Num);
            fltGrdSum.setLevel3Number(fltGrdLvl3Num);
        }
        return fltGrdSum;
    }

    static int queryDCFrqByOneDate(Set<DrivingCycle> drivingCycleSet){
        return drivingCycleSet.size();
    }


}
