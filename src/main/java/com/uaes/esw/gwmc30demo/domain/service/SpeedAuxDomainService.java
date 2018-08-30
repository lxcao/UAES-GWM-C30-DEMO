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

import com.uaes.esw.gwmc30demo.domain.model.entity.can.VCU77CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.scenario.speedAuxiliary.SAStatus;
import com.uaes.esw.gwmc30demo.domain.model.scenario.speedAuxiliary.SAStatusNotice;
import com.uaes.esw.gwmc30demo.domain.model.scenario.speedAuxiliary.SetSAReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.speedAuxiliary.SetSARes;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;
import com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;
import static com.uaes.esw.gwmc30demo.domain.repository.can.ICanRepository.getLastVCU77MessageFromRedis;

public interface SpeedAuxDomainService {
    static SetSARes setSADomainService(SetSAReq setSAReq){
        IVehicleRepository.sendSpeedAux2Vehicle(setSAReq.getSpeedAux());
        SetSARes setASRes = SetSARes.builder().driver(setSAReq.getDriver())
                .dateTime(setSAReq.getDateTime()).responseCode(RESPONSE_CODE_SUCCESS).build();
        return setASRes;
    }

    static SAStatusNotice createSpeedAuxStatusNotice(){
        VCU77CanMessage vcu77CanMessage = getLastVCU77MessageFromRedis();
        SAStatus saStatus = SAStatus.builder().CrrSpd(vcu77CanMessage.getCrr_Spd())
                .SpdLmtEnableFlag(vcu77CanMessage.getSpd_Lmt_Enable_Flag())
                .SpdOverrunFlag(vcu77CanMessage.getSpd_Overrun_Flag())
                .build();
        SAStatusNotice saStatusNotice = SAStatusNotice.builder().speedAuxStatus(saStatus)
                .dateTime(DateTimeUtils.getDateTimeString()).build();
        return saStatusNotice;
    }
}
