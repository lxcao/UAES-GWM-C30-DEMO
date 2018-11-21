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

package com.uaes.esw.gwmc30demo.test.infrastructure.http;

import com.uaes.esw.gwmc30demo.infrastructure.http.HttpClientHandler;

import java.util.HashMap;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.*;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_URL_SENIVERSE_UNIT_VALUE;

public class HttpClientTest {

    public static void httpClientT(){
        String url = HTTP_URL_SENIVERSE_WEATHER_NOW_URL;
        Map<String,Object> params =  new HashMap<>();
        params.put(HTTP_URL_SENIVERSE_LOCATION_KEY,"shanghai");
        params.put(HTTP_URL_SENIVERSE_KEY_KEY,HTTP_URL_SENIVERSE_KEY_VALUE);
        params.put(HTTP_URL_SENIVERSE_LANGUAGE_KEY,HTTP_URL_SENIVERSE_LANGUAGE_VALUE);
        params.put(HTTP_URL_SENIVERSE_UNIT_KEY,HTTP_URL_SENIVERSE_UNIT_VALUE);
        try{
            System.out.println(HttpClientHandler.httpGetRequest(url,params));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        httpClientT();
    }
}
