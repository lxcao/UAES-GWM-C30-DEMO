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

package com.uaes.esw.gwmc30demo.domain.model.entity.speedAux;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class SpeedAux {
    int SpdAuxMode; //车速辅助模式
    int SpdLmtType; //车速限制类型
    int CrrRoadSpdLmt; //当前道路限速值
    int CstSpdLmt; //客户自定义限速值
}
