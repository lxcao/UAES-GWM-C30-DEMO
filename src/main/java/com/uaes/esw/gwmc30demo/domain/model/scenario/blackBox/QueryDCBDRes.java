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

package com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.entity.journey.Journey;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data @Builder
public class QueryDCBDRes {
    Driver driver;
    String dateTime;
    String queryDate; //查询的日期
    int drivingCycleFrq; // 驾驶循环的次数
    Set<DrivingCycle> drivingCycles; //驾驶循环
    String responseCode;
}
