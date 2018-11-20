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

package com.uaes.esw.gwmc30demo.test.infrastructure.json;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.VCU79CanMessage;
import org.junit.Test;

import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;

public class JSONUtilityTest {

    @Test
    public void testTransferFromJSON2Object() {
        String vcu79String = "{\"Accident_Flg\":1,\"Acc_Pdl_Flt_Grd\":2,\"Brk_Pdl_Flt_Grd\":0,\"Gear_Flt_Grd\":0,\"Spd_Flt_Grd\":0,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"LowVltg\":7.8 ,\"id\":\"79\",\"unixtimestamp\":1539681371133}";
        VCU79CanMessage vcu79CanMessage = transferFromJSON2Object(vcu79String, VCU79CanMessage.class);
        System.out.println(vcu79CanMessage.toString());
    }

    @Test
    public void testTransferFromObject2JSON() {
    }

    @Test
    public void testIsJSONValid() {
    }
}