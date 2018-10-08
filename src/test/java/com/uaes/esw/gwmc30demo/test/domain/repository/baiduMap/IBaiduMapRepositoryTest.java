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

package com.uaes.esw.gwmc30demo.test.domain.repository.baiduMap;

import com.uaes.esw.gwmc30demo.domain.model.entity.geography.GeoLocation;
import com.uaes.esw.gwmc30demo.domain.model.entity.geography.aGPS;
import org.junit.Test;

import static com.uaes.esw.gwmc30demo.domain.repository.baiduMap.IBaiduMapRepository.*;

public class IBaiduMapRepositoryTest {
    @Test
    public void testQueryCurrentLocationLatLng() {
        queryLngLatByCurrentLocation("浦东机场","上海市");
        queryLngLatByCurrentLocation("联合汽车电子有限公司","上海市");
    }

    @Test
    public void testQueryAddressByLngLat() {
        GeoLocation location = queryLatLngByLocation("联合汽车电子有限公司","上海市");
        queryAddressByBD09LngLat(location.getBd09GPS().getLng(),location.getBd09GPS().getLat());
    }

    @Test
    public void testCalTargetLocationV2(){
        calTargetLocationV2(queryLngLatByCurrentLocation("联合汽车电子有限公司","上海市"));
    }

    @Test
    public void testPlanRouteByBD09InShanghai() {
        GeoLocation original = queryLatLngByLocation("联合汽车电子有限公司","上海市");
        GeoLocation destination = queryLatLngByLocation("联合汽车电子有限公司","上海市");
        aGPS originalBD09 = aGPS.builder().lat(original.getBd09GPS().getLat())
                .lng(original.getBd09GPS().getLng()).build();
        aGPS destinationBD09 = aGPS.builder().lat(destination.getBd09GPS().getLat())
                .lng(destination.getBd09GPS().getLng()).build();
        planRouteByBD09InShanghai(originalBD09,destinationBD09);
    }

}