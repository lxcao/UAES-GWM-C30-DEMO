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

package com.uaes.esw.gwmc30demo.domain.repository.gpsSpg;

import com.uaes.esw.gwmc30demo.domain.model.entity.geography.aGPS;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.COMMA;
import static com.uaes.esw.gwmc30demo.constant.GPSspgConstants.*;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpClientHandler.httpGetRequest;

public interface IGPSspgRepository {

    //转换百度BD09到标准大地坐标
    static aGPS convertBD09toWGS84(aGPS bd09){
        return convertGPSByGPSspg(bd09, GPS_SPG_API_COORD_TYPE_BD09,GPS_SPG_API_COORD_TYPE_WGS84);
    }

    //转换百度BD09到标准大地坐标
    static aGPS convertWGS84toBD09(aGPS wgs84){
        return convertGPSByGPSspg(wgs84, GPS_SPG_API_COORD_TYPE_WGS84,GPS_SPG_API_COORD_TYPE_BD09);
    }

    //通过GPSspg转换坐标系
    static aGPS convertGPSByGPSspg(aGPS inputGPS, int from, int to){
        String url = GPS_SPG_API_URL + GPS_SPG_API_CONVERT;
        Map<String,Object> params =  new LinkedHashMap<>();
        params.put(GPS_SPG_API_OID_KEY, GPS_SPG_API_OID_VALUE);
        params.put(GPS_SPG_API_KEY_KEY,GPS_SPG_API_KEY_VALUE);
        params.put(GPS_SPG_API_FROM_KEY, from);
        params.put(GPS_SPG_API_TO_KEY, to);
        params.put(GPS_SPG_API_LATLNG_KEY, String.valueOf(String.valueOf(inputGPS.getLat())
                +COMMA+String.valueOf(inputGPS.getLng())));
        String convertResult = httpGetRequest(url,params);
        System.out.println(convertResult);
        JSONObject convertResultObj = new JSONObject(convertResult);
        if(GPS_SPG_API_STATUS_SUCCESS_VALUE == convertResultObj.getInt(GPS_SPG_API_STATUS_KEY)){
            return aGPS.builder()
                    .lat(Double.parseDouble(convertResultObj.getJSONArray(GPS_SPG_API_RESULT_KEY)
                            .getJSONObject(GPS_SPG_API_RESULT_INDEX).getString(GPS_SPG_API_LAT_KEY)))
                    .lng(Double.parseDouble(convertResultObj.getJSONArray(GPS_SPG_API_RESULT_KEY)
                            .getJSONObject(GPS_SPG_API_RESULT_INDEX).getString(GPS_SPG_API_LNG_KEY)))
                    .build();
        }
        else
            return null;
    }
}
