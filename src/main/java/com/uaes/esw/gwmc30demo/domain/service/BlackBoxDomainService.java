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

import java.util.Set;
import java.util.stream.Stream;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.*;

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
            dc.setFltGrdSum(calFaultGradeSummaryByDrivingCycle(dc.getPowerOnTimestamp(),
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

    static FltGrdSum calFaultGradeSummaryByDrivingCycle(long dcStartTime, long dcEndTime){
        Set<VCU79CanMessage> vcu79CanMessagesSet = ICanRepository.
                getVCU79MessageFromRedisByPeriod(dcStartTime, dcEndTime);
        // 初始化
        FltGrdSum fltGrdSum = FltGrdSum.builder()
                .HasFlt(false).Level1Number(0).Level2Number(0).Level3Number(0).build();
        Stream<Integer> accPdlFltGrdArray = null, brkPdlFltGrdArray = null, gearFltGrdArray = null, spdFltGrdArray = null;
        Stream<Integer> drvMtrFltGrdArray = null, dCDCFltGrdArray = null, btyFltGrdArray = null;
        //判断是否有故障
        if(vcu79CanMessagesSet.parallelStream()
                .filter(v -> v.getAccident_Flg() == 1).findAny().isPresent()){
            fltGrdSum.setHasFlt(true);
            //加速踏板故障等级
            if(vcu79CanMessagesSet.parallelStream()
                    .filter(s -> s.getAcc_Pdl_Flt_Grd() != 0).findAny().isPresent())
                accPdlFltGrdArray = vcu79CanMessagesSet.parallelStream()
                        .filter(s -> s.getAcc_Pdl_Flt_Grd() != 0)
                        .mapToInt(v -> v.getAcc_Pdl_Flt_Grd()).distinct().boxed();
            //制动踏板故障等级
            if(vcu79CanMessagesSet.parallelStream()
                    .filter(s -> s.getBrk_Pdl_Flt_Grd() != 0).findAny().isPresent())
                brkPdlFltGrdArray = vcu79CanMessagesSet.parallelStream()
                        .filter(s -> s.getBrk_Pdl_Flt_Grd() != 0)
                        .mapToInt(v -> v.getBrk_Pdl_Flt_Grd()).distinct().boxed();
            //档位故障等级
            if(vcu79CanMessagesSet.parallelStream()
                    .filter(s -> s.getGear_Flt_Grd() != 0).findAny().isPresent())
                gearFltGrdArray = vcu79CanMessagesSet.parallelStream()
                        .filter(s -> s.getGear_Flt_Grd() != 0)
                        .mapToInt(v -> v.getGear_Flt_Grd()).distinct().boxed();
            //车速故障等级
            if(vcu79CanMessagesSet.parallelStream()
                    .filter(s -> s.getSpd_Flt_Grd() != 0).findAny().isPresent())
                spdFltGrdArray = vcu79CanMessagesSet.parallelStream()
                        .filter(s -> s.getSpd_Flt_Grd() != 0)
                        .mapToInt(v -> v.getSpd_Flt_Grd()).distinct().boxed();
            //驱动电机故障等级
            if(vcu79CanMessagesSet.parallelStream()
                    .filter(s -> s.getDrv_Mtr_Flt_Grd() != 0).findAny().isPresent())
                drvMtrFltGrdArray = vcu79CanMessagesSet.parallelStream()
                        .filter(s -> s.getDrv_Mtr_Flt_Grd() != 0)
                        .mapToInt(v -> v.getDrv_Mtr_Flt_Grd()).distinct().boxed();
            //DCDC故障等级
            if(vcu79CanMessagesSet.parallelStream()
                    .filter(s -> s.getDCDC_Flt_Grd() != 0).findAny().isPresent())
                dCDCFltGrdArray = vcu79CanMessagesSet.parallelStream()
                        .filter(s -> s.getDCDC_Flt_Grd() != 0)
                        .mapToInt(v -> v.getDCDC_Flt_Grd()).distinct().boxed();
            //电池故障等级
            if(vcu79CanMessagesSet.parallelStream()
                    .filter(s -> s.getBty_Flt_Grd() != 0).findAny().isPresent())
                btyFltGrdArray = vcu79CanMessagesSet.parallelStream()
                        .filter(s -> s.getBty_Flt_Grd() != 0)
                        .mapToInt(v -> v.getBty_Flt_Grd()).distinct().boxed();
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
