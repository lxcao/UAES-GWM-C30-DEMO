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

package com.uaes.esw.gwmc30demo.domain.repository.baiduMap;

import com.uaes.esw.gwmc30demo.domain.model.entity.geography.GeoLocation;
import com.uaes.esw.gwmc30demo.domain.model.entity.geography.WGS84;
import com.uaes.esw.gwmc30demo.domain.model.entity.geography.aGPS;
import com.uaes.esw.gwmc30demo.domain.model.scenario.SOEMileageRadiusPlan.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.BaiduMapConstants.*;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.*;
import static com.uaes.esw.gwmc30demo.constant.SOEMileageRadiusPlanConstants.*;
import static com.uaes.esw.gwmc30demo.domain.repository.gpsSpg.IGPSspgRepository.convertBD09toWGS84;
import static com.uaes.esw.gwmc30demo.domain.repository.gpsSpg.IGPSspgRepository.convertWGS84toBD09;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpClientHandler.httpGetRequest;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.getDateTimeString;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.MD5utils.stackoverFlowMD5;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.MathUtils.Deg2Rad;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.MathUtils.Rad2Deg;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.StringUtils.toQueryString;

public interface IBaiduMapRepository {

    //生成sn
    static String createSN(String baiduMapURL, String sk, Map<String, Object> urlParams){
        // 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存<key,value>，该方法根据key的插入顺序排序；post请使用TreeMap保存<key,value>，该方法会自动将key按照字母a-z顺序排序。所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。以get请求为例：http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。
        try{
            // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
            String paramsStr = toQueryString(urlParams);

            // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
            String wholeStr = new String(baiduMapURL + paramsStr + sk);
            //System.out.println(wholeStr);
            // 对上面wholeStr再作utf8编码
            String tempStr = URLEncoder.encode(wholeStr, CHARACTOERSET_UTF8);
            // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
            String strMD5 = stackoverFlowMD5(tempStr);
            //System.out.println(strMD5);
            return strMD5;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //根据不同的方位计算球面距离得到的方位
    static aGPS calTargetWgs84(aGPS sourceWgs84, double bearing, double arcLength){
        double lat1 = Deg2Rad(sourceWgs84.getLat());
        double lng1 = Deg2Rad(sourceWgs84.getLng());
        double centralAngle = arcLength / EARTH_REDIUS;
        double lat2 = Math.asin(Math.sin(lat1)*Math.cos(centralAngle) +
                Math.cos(lat1)*Math.sin(centralAngle)*Math.cos(Deg2Rad(bearing)));
        double lng2 = lng1 +
                Math.atan2(Math.sin(Deg2Rad(bearing))*Math.sin(centralAngle)*Math.cos(lat1),
                        Math.cos(centralAngle)-Math.sin(lat1)*Math.sin(lat2));
        aGPS wgs84 = WGS84.builder().lng(Rad2Deg(lng2)).lat(Rad2Deg(lat2)).build();
        return wgs84;
    }

    //计算目标Lng
    static double calTargetWgs84Lng(double sourceWgs84Lng, double index){
        return sourceWgs84Lng + MAX_BATTERY_LIFE_MILEAGE*Math.cos(index*Math.PI/TARGET_LOCATION_NUMBER_DOUBLE);
    }

    //计算目标Lat
    static double calTargetWgs84Lat(double sourceWgs84Lat,double index){
        return sourceWgs84Lat + MAX_BATTERY_LIFE_MILEAGE*Math.sin(index*Math.PI/TARGET_LOCATION_NUMBER_DOUBLE);
    }

    //修正目标Lng
    static double refineTargetWgs84Lng(double currentWgs84Lng, double index){
        return currentWgs84Lng - TARGET_LOCATION_REFINE_PARAMETER*Math.cos(index*Math.PI/TARGET_LOCATION_NUMBER_DOUBLE);
    }
    //修正目标Lat
    static double refineTargetWgs84Lat(double currentWgs84Lat, double index){
        return currentWgs84Lat - TARGET_LOCATION_REFINE_PARAMETER*Math.sin(index*Math.PI/TARGET_LOCATION_NUMBER_DOUBLE);
    }

    //查询当前位置的经纬度
    static CurrentLocation queryLngLatByCurrentLocation(String cLocation, String cCity){
        return CurrentLocation.builder()
                .dateTime(getDateTimeString())
                .currentGeoLocation(queryLatLngByLocation(cLocation,cCity))
                .build();
    }

    //计算目标GPS点集v2
    static TargetLocation calTargetLocationV2(CurrentLocation currentLocation){
        TargetLocation targetLocation = TargetLocation.builder()
                .dateTime(currentLocation.getDateTime()).build();
        List<GeoLocation> targetGeoLocationList = new ArrayList<GeoLocation>();
        aGPS currentWgs84 = currentLocation.getCurrentGeoLocation().getWgs84GPS();

        for(int i=1;i <= TARGET_LOCATION_NUMBER_INT; i++){
            System.out.println("开始计算"+i);
            System.out.println("currentWgs84Lng:"+currentWgs84.getLng());
            System.out.println("currentWgs84Lat:"+currentWgs84.getLat());
            aGPS targetWgs84 = calTargetWgs84(currentWgs84,
                    (double)i/TARGET_LOCATION_NUMBER_DOUBLE*DEGREE_360,
                    MAX_BATTERY_LIFE_MILEAGE);
            System.out.println("targetWgs84lng:"+targetWgs84.getLng());
            System.out.println("targetWgs84lat:"+targetWgs84.getLat());
            aGPS targetBd09 = convertWGS84toBD09(targetWgs84);
            //TODO: need add meaningful
            GeoLocation targetGeoLocation = GeoLocation.builder()
                    .bd09GPS(targetBd09).wgs84GPS(targetWgs84).build();
            targetGeoLocationList.add(i-1,targetGeoLocation);
/*            targetGeoLocation.add(i-1,calMeaningfulAddressByBD09LngLat(targetBd09.getLng(),
                    targetBd09.getLat(), (double) i));*/
        }
        System.out.println(targetGeoLocationList);
        targetLocation.setTargetGeoLocation(targetGeoLocationList);
        return targetLocation;
    }

    //计算目标GPS点集
    static TargetLocation calTargetLocation(CurrentLocation currentLocation){
        TargetLocation targetLocation = TargetLocation.builder()
                .dateTime(currentLocation.getDateTime()).build();
        List<GeoLocation> targetGeoLocation = new ArrayList<GeoLocation>();
        double currentWgs84Lng = currentLocation.getCurrentGeoLocation()
                .getWgs84GPS().getLng();
        double currentWgs84Lat = currentLocation.getCurrentGeoLocation()
                .getWgs84GPS().getLat();
        for(int i=1;i < TARGET_LOCATION_NUMBER_INT; i++){
            System.out.println("开始计算"+i);
            System.out.println("currentWgs84Lng:"+currentWgs84Lng);
            System.out.println("currentWgs84Lat:"+currentWgs84Lat);
            double targetWgs84lng = calTargetWgs84Lng(currentWgs84Lng,(double) i);
            double targetWgs84lat = calTargetWgs84Lat(currentWgs84Lat,(double) i);
            System.out.println("targetWgs84lng:"+targetWgs84lng);
            System.out.println("targetWgs84lat:"+targetWgs84lat);
            aGPS wgs84 = aGPS.builder().lat(targetWgs84lat).lng(targetWgs84lng).build();
            aGPS bd09 = convertWGS84toBD09(wgs84);
            targetGeoLocation.add(i,calMeaningfulAddressByBD09LngLat(bd09.getLng(),
                    bd09.getLat(), (double) i));
        }
        targetLocation.setTargetGeoLocation(targetGeoLocation);
        System.out.println(targetGeoLocation);
        return targetLocation;
    }

    //递归查询有地理含义的经纬度的逆地理编码
    static GeoLocation calMeaningfulAddressByBD09LngLat(double bd09Lng, double bd09Lat, double index){
        GeoLocation geoLocation = queryAddressByBD09LngLat(bd09Lng, bd09Lat);
        //TODO: reprogram
        if(geoLocation.getAddress().equals(BAIDU_MAP_API_RESULT_FORMATTED_ADDRESS_UNKNOWN_VALUE)){
            aGPS bd09 = convertWGS84toBD09(convertBD09toWGS84(aGPS.builder()
                    .lng(bd09Lng).lat(bd09Lat).build()));
            calMeaningfulAddressByBD09LngLat(refineTargetWgs84Lng(bd09.getLng(), index),
                    refineTargetWgs84Lat(bd09.getLat(), index), index);
        }
        return geoLocation;
    }

    //查询百度的经纬度的逆地理编码
    static GeoLocation queryAddressByBD09LngLat(double lng, double lat){
        String url = BAIDU_MAP_API_URL + BAIDU_MAP_API_GEOCODER;
        Map<String,Object> params =  new LinkedHashMap<>();
        params.put(BAIDU_MAP_API_LOCATION_KEY,String.valueOf(lat)
                +BAIDU_MAP_API_COMMA_SYMBOL+String.valueOf(lng));
        params.put(BAIDU_MAP_API_OUTPUT_KEY,BAIDU_MAP_API_OUTPUT_VALUE);
        params.put(BAIDU_MAP_API_AK_KEY,BAIDU_MAP_API_AK_VALUE);

        String locationAddressResult = httpGetRequest(url,params);
        System.out.println(locationAddressResult);
        JSONObject locationAddressResultJSON = new JSONObject(locationAddressResult);
        if(BAIDU_MAP_API_RESULT_STATUS_SUCCESS_VALUE ==
                locationAddressResultJSON.getInt(BAIDU_MAP_API_RESULT_STATUS_KEY)){
            String formattedAddress = locationAddressResultJSON
                    .getJSONObject(BAIDU_MAP_API_RESULT_RESULT_KEY)
                    .getString(BAIDU_MAP_API_RESULT_FORMATTED_ADDRESS_KEY);
            String addressStr = BAIDU_MAP_API_RESULT_FORMATTED_ADDRESS_UNKNOWN_VALUE;
            if(!formattedAddress.equals(""))
                addressStr = formattedAddress;
            aGPS bd09 = aGPS.builder().lat(lat).lng(lng).build();
            aGPS wgs84 = convertBD09toWGS84(bd09);
            GeoLocation geoLocation = GeoLocation.builder().location(addressStr)
                    .address(addressStr).bd09GPS(bd09).wgs84GPS(wgs84).build();
            System.out.println(geoLocation);
            return geoLocation;
        }
        else
            return null;
    }

    //查询位置的百度的经纬度，并转换为大地作弊系，实际用的是白名单，所以不需要SN，但有SN也无妨
    static GeoLocation queryLatLngByLocation(String location, String city){
        String url = BAIDU_MAP_API_URL + BAIDU_MAP_API_GEOCODER;
        Map<String,Object> params =  new LinkedHashMap<>();
        params.put(BAIDU_MAP_API_ADDRESS_KEY,location);
        params.put(BAIDU_MAP_API_CITY_KEY,city);
        params.put(BAIDU_MAP_API_OUTPUT_KEY,BAIDU_MAP_API_OUTPUT_VALUE);
        params.put(BAIDU_MAP_API_AK_KEY,BAIDU_MAP_API_AK_VALUE);
        //计算SN,不是必须
        String mySN = createSN(BAIDU_MAP_API_GEOCODER + BAIDU_MAP_API_QUESTION_SYMBOL,
                BAIDU_MAP_API_SK_VALUE, params);
        params.put(BAIDU_MAP_API_SN_KEY,mySN);

        String locationLatLngResult = httpGetRequest(url,params);
        System.out.println(locationLatLngResult);
        JSONObject locationLatLngResultJSON = new JSONObject(locationLatLngResult);
        if(BAIDU_MAP_API_RESULT_STATUS_SUCCESS_VALUE == locationLatLngResultJSON
                .getInt(BAIDU_MAP_API_RESULT_STATUS_KEY)){
            double lat = locationLatLngResultJSON.getJSONObject(BAIDU_MAP_API_RESULT_RESULT_KEY)
                    .getJSONObject(BAIDU_MAP_API_RESULT_LOCATION_KEY).getDouble(BAIDU_MAP_API_RESULT_LAT_KEY);
            double lng = locationLatLngResultJSON.getJSONObject(BAIDU_MAP_API_RESULT_RESULT_KEY)
                    .getJSONObject(BAIDU_MAP_API_RESULT_LOCATION_KEY).getDouble(BAIDU_MAP_API_RESULT_LNG_KEY);
            aGPS bd09 = aGPS.builder().lat(lat).lng(lng).build();
            aGPS wgs84 = convertBD09toWGS84(bd09);
            GeoLocation geoLocation = GeoLocation.builder().location(location).address(location).bd09GPS(bd09)
                    .wgs84GPS(wgs84).build();
            System.out.println(geoLocation);
            return geoLocation;
        }
        else
            return null;
    }

    //路线规划
    static PlanRoute planRouteByBD09InShanghai(aGPS originBD09, aGPS destinationBD09){
        String url = BAIDU_MAP_API_URL + BAIDU_MAP_API_DIRECTION_V1;
        Map<String,Object> params =  new LinkedHashMap<>();
        params.put(BAIDU_MAP_API_ORIGIN_KEY,
                String.format(STRING_FORMAT_REMAIN_SIX_AFTER_DECIMAL_POINT,originBD09.getLat()) +
                        COMMA + String.format(STRING_FORMAT_REMAIN_SIX_AFTER_DECIMAL_POINT,originBD09.getLng()));
        params.put(BAIDU_MAP_API_DESTINATION_KEY,
                String.format(STRING_FORMAT_REMAIN_SIX_AFTER_DECIMAL_POINT,destinationBD09.getLat()) +
                        COMMA + String.format(STRING_FORMAT_REMAIN_SIX_AFTER_DECIMAL_POINT,destinationBD09.getLng()));
        params.put(BAIDU_MAP_API_ORIGIN_REGION_KEY,BAIDU_MAP_API_REGION_VALUE);
        params.put(BAIDU_MAP_API_DESTINATION_REGION_KEY,BAIDU_MAP_API_REGION_VALUE);
        params.put(BAIDU_MAP_API_OUTPUT_KEY,BAIDU_MAP_API_OUTPUT_VALUE);
        params.put(BAIDU_MAP_API_AK_KEY,BAIDU_MAP_API_AK_VALUE);

        String planRouteResult = httpGetRequest(url,params);
        System.out.println(planRouteResult);

        JSONObject planRouteResultObj = new JSONObject(planRouteResult);

        List<Direction> directions = new ArrayList<>();
        if(BAIDU_MAP_API_RESULT_STATUS_SUCCESS_VALUE == planRouteResultObj
                .getInt(BAIDU_MAP_API_RESULT_STATUS_KEY)){
            JSONArray routesArray = planRouteResultObj.getJSONObject(BAIDU_MAP_API_RESULT_RESULT_KEY)
                    .getJSONArray(BAIDU_MAP_API_ROUTES_KEY);
            int routesNumber = routesArray.length();
            System.out.println("There are " + routesNumber + " routes in this result");
            for(int j = 0; j< routesNumber; j++){
                JSONObject routeObj = routesArray.getJSONObject(j);
                Route summaryRoute = Route.builder().distance(routeObj.getInt(BAIDU_MAP_API_DISTANCE_KEY))
                        .duration(routeObj.getInt(BAIDU_MAP_API_DURATION_KEY))
                        .build();
                System.out.println("summaryRoute:"+summaryRoute);
                List<Route> stepsList = new ArrayList<>();
                List<Integer> stepsAccumDurationList = new ArrayList<>();
                int stepsAccumDuration = 0;
                JSONArray stepsArray = routeObj.getJSONArray(BAIDU_MAP_API_STEPS_KEY);
                int stepsNumber = stepsArray.length();
                System.out.println("There are " + stepsNumber + " steps in this result");
                for(int i = 0; i < stepsNumber; i++){
                    int stepDuration = stepsArray.getJSONObject(i).getInt(BAIDU_MAP_API_DURATION_KEY);
                    int stepDistance = stepsArray.getJSONObject(i).getInt(BAIDU_MAP_API_DISTANCE_KEY);
                    Route route = Route.builder().distance(stepDistance).duration(stepDuration).build();
                    stepsList.add(i,route);
                    stepsAccumDuration = stepsAccumDuration + stepDuration;
                    stepsAccumDurationList.add(i,stepsAccumDuration);
                }
                Direction direction = Direction.builder().durationAccum(stepsAccumDurationList)
                        .steps(stepsList).summary(summaryRoute).build();
                System.out.println(direction);
                directions.add(j,direction);
            }
            PlanRoute planRoute = PlanRoute.builder().directions(directions).build();
            return planRoute;
        }
        else
            return null;
    }

}
