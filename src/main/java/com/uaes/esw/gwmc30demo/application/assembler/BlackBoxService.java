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

package com.uaes.esw.gwmc30demo.application.assembler;

import com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox.QueryDCBDReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox.QueryDCBDRes;
import com.uaes.esw.gwmc30demo.domain.service.LogInDomainService;

import static com.uaes.esw.gwmc30demo.domain.service.BlackBoxDomainService.queryDCBDDomainService;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface BlackBoxService {
    static String queryDCByDate(String queryDCBDString){
        QueryDCBDReq queryDCBDReq = transferFromJSON2Object(queryDCBDString, QueryDCBDReq.class);
        if(LogInDomainService.notLogInDomainService(queryDCBDReq.getDriver(),queryDCBDReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(queryDCBDReq.getDriver(),queryDCBDReq.getDateTime());
        QueryDCBDRes queryDCBDRes =queryDCBDDomainService(queryDCBDReq);
        return transferFromObject2JSON(queryDCBDRes);
    }
}
