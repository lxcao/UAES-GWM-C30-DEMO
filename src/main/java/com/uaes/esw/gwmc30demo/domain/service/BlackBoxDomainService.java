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

import com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox.DrivingCycle;
import com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox.QueryDCBDReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox.QueryDCBDRes;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;

import java.util.Set;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.*;

public interface BlackBoxDomainService {
    static QueryDCBDRes queryDCBDDomainService(QueryDCBDReq queryDCBDReq){
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
        return IVehicleRepository.getDrivingCycleByPeriod(queryDate + SPACE + TIME_BEGIN,
                queryDate + SPACE + TIME_END);
    }

    static int queryDCFrqByOneDate(Set<DrivingCycle> drivingCycleSet){
        return drivingCycleSet.size();
    }


}
