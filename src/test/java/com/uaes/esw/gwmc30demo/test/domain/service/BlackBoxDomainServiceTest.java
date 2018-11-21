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

package com.uaes.esw.gwmc30demo.test.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.VCU79CanMessage;
import com.uaes.esw.gwmc30demo.domain.service.BlackBoxDomainService;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static org.junit.Assert.*;

public class BlackBoxDomainServiceTest {
    String v79_0 = "{\"Accident_Flg\":1,\"Acc_Pdl_Flt_Grd\":2,\"Brk_Pdl_Flt_Grd\":1,\"Gear_Flt_Grd\":0,\"Spd_Flt_Grd\":0,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"id\":\"79\",\"unixtimestamp\":1542338327000}";
    String v79_1 = "{\"Accident_Flg\":1,\"Acc_Pdl_Flt_Grd\":1,\"Brk_Pdl_Flt_Grd\":0,\"Gear_Flt_Grd\":0,\"Spd_Flt_Grd\":0,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"id\":\"79\",\"unixtimestamp\":1542338328000}";
    String v79_2 = "{\"Accident_Flg\":1,\"Acc_Pdl_Flt_Grd\":2,\"Brk_Pdl_Flt_Grd\":0,\"Gear_Flt_Grd\":0,\"Spd_Flt_Grd\":0,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"id\":\"79\",\"unixtimestamp\":1542338329000}";
    String v79_3 = "{\"Accident_Flg\":1,\"Acc_Pdl_Flt_Grd\":1,\"Brk_Pdl_Flt_Grd\":0,\"Gear_Flt_Grd\":0,\"Spd_Flt_Grd\":0,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"id\":\"79\",\"unixtimestamp\":1542338330000}";
    String v79_4 = "{\"Accident_Flg\":1,\"Acc_Pdl_Flt_Grd\":2,\"Brk_Pdl_Flt_Grd\":0,\"Gear_Flt_Grd\":3,\"Spd_Flt_Grd\":0,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"id\":\"79\",\"unixtimestamp\":1542338331000}";
    String v79_5 = "{\"Accident_Flg\":1,\"Acc_Pdl_Flt_Grd\":2,\"Brk_Pdl_Flt_Grd\":0,\"Gear_Flt_Grd\":0,\"Spd_Flt_Grd\":1,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"id\":\"79\",\"unixtimestamp\":1542338332000}";
    String v79_6 = "{\"Accident_Flg\":0,\"Acc_Pdl_Flt_Grd\":0,\"Brk_Pdl_Flt_Grd\":2,\"Gear_Flt_Grd\":0,\"Spd_Flt_Grd\":0,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"id\":\"79\",\"unixtimestamp\":1542338333000}";
    String v79_7 = "{\"Accident_Flg\":1,\"Acc_Pdl_Flt_Grd\":1,\"Brk_Pdl_Flt_Grd\":0,\"Gear_Flt_Grd\":2,\"Spd_Flt_Grd\":0,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"id\":\"79\",\"unixtimestamp\":1542338334000}";
    String v79_8 = "{\"Accident_Flg\":1,\"Acc_Pdl_Flt_Grd\":1,\"Brk_Pdl_Flt_Grd\":0,\"Gear_Flt_Grd\":0,\"Spd_Flt_Grd\":0,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"id\":\"79\",\"unixtimestamp\":1542338335000}";
    String v79_9 = "{\"Accident_Flg\":0,\"Acc_Pdl_Flt_Grd\":0,\"Brk_Pdl_Flt_Grd\":3,\"Gear_Flt_Grd\":0,\"Spd_Flt_Grd\":0,\"Drv_Mtr_Flt_Grd\":0,\"DCDC_Flt_Grd\":0,\"Bty_Flt_Grd\":0,\"Acc_Pdl_Flt_Desc\":0,\"Vltg_1st\":3.4,\"Vltg_2nd\":5.6,\"id\":\"79\",\"unixtimestamp\":1542338336000}";

    Set<VCU79CanMessage> vcu79CanMessageSet = new HashSet<>();


    @Before
    public void dataPrepare(){
        vcu79CanMessageSet.add(transferFromJSON2Object(v79_0, VCU79CanMessage.class));
        vcu79CanMessageSet.add(transferFromJSON2Object(v79_1, VCU79CanMessage.class));
        vcu79CanMessageSet.add(transferFromJSON2Object(v79_2, VCU79CanMessage.class));
        vcu79CanMessageSet.add(transferFromJSON2Object(v79_3, VCU79CanMessage.class));
        vcu79CanMessageSet.add(transferFromJSON2Object(v79_4, VCU79CanMessage.class));
        vcu79CanMessageSet.add(transferFromJSON2Object(v79_5, VCU79CanMessage.class));
        vcu79CanMessageSet.add(transferFromJSON2Object(v79_6, VCU79CanMessage.class));
        vcu79CanMessageSet.add(transferFromJSON2Object(v79_7, VCU79CanMessage.class));
        vcu79CanMessageSet.add(transferFromJSON2Object(v79_8, VCU79CanMessage.class));
        vcu79CanMessageSet.add(transferFromJSON2Object(v79_9, VCU79CanMessage.class));
    }

    @Test
    public void testIsFaultHappened() {
        System.out.println(BlackBoxDomainService.isFaultHappened(vcu79CanMessageSet));
    }

    @Test
    public void testIsAccPdlFltHappened() {
        System.out.println(BlackBoxDomainService.isAccPdlFltHappened(vcu79CanMessageSet));
    }

    @Test
    public void testGetAccPdlFltGrd(){
        BlackBoxDomainService.getAccPdlFltGrd(vcu79CanMessageSet).forEach(v -> System.out.println(v));
    }

    @Test
    public void testGetAccPdlFltGrdAndList() {

        Map<Integer, List<VCU79CanMessage>> mapResult =  BlackBoxDomainService.getAccPdlFltGrdAndList(vcu79CanMessageSet);
        System.out.println(mapResult);
    }

    @Test
    public void testCalSumFaultGradeSummary() {
        System.out.println(BlackBoxDomainService.calSumFaultGradeSummary(vcu79CanMessageSet));
    }
}