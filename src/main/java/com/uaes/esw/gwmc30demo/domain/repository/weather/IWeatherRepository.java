package com.uaes.esw.gwmc30demo.domain.repository.weather;

import com.uaes.esw.gwmc30demo.constant.InfraRedisConstants;
import com.uaes.esw.gwmc30demo.domain.model.entity.weather.AirNow;
import com.uaes.esw.gwmc30demo.domain.model.entity.weather.Weather;
import com.uaes.esw.gwmc30demo.domain.model.entity.weather.WeatherNow;
import com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.*;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_WEATHER_ZSET;
import static com.uaes.esw.gwmc30demo.constant.WeatherConstants.*;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpClientHandler.httpGetRequest;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.getLastOneStringFromZset;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.inputValue2ZSET;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.zRangeByScore;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.getDateTimeString;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.transfer2UnixTime;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.commonLogInfo;
import static java.util.stream.Collectors.groupingBy;

public interface IWeatherRepository {

    //查询WeatherNow
    static WeatherNow queryWeatherNow(String location){
        WeatherNow weatherNow = WeatherNow.builder().build();
        String url = HTTP_URL_SENIVERSE_WEATHER_NOW_URL;
        Map<String,Object> params =  new HashMap<>();
        params.put(HTTP_URL_SENIVERSE_LOCATION_KEY,location);
        params.put(HTTP_URL_SENIVERSE_KEY_KEY,HTTP_URL_SENIVERSE_KEY_VALUE);
        params.put(HTTP_URL_SENIVERSE_LANGUAGE_KEY,HTTP_URL_SENIVERSE_LANGUAGE_VALUE);
        params.put(HTTP_URL_SENIVERSE_UNIT_KEY,HTTP_URL_SENIVERSE_UNIT_VALUE);
        try{
            commonLogInfo("Start queryWeatherNow @ "+location);
            String weatherNowResult = httpGetRequest(url,params);
            commonLogInfo("Get WeatherNow="+weatherNowResult);
            JSONObject weatherNowResultJSONObj = new JSONObject(weatherNowResult);
            JSONObject resultJSONObj = weatherNowResultJSONObj.getJSONArray(WEATHER_JSON_KEY_RESULT)
                    .getJSONObject(WEATHER_JSON_ARRAY_INDEX);
            String weatherText = resultJSONObj.getJSONObject(WEATHER_JSON_KEY_NOW)
                    .getString(WEATHER_JSON_KEY_TEXT);
            weatherNow.setWeatherText(weatherText);
            String weatherCode = resultJSONObj.getJSONObject(WEATHER_JSON_KEY_NOW)
                    .getString(WEATHER_JSON_KEY_CODE);
            int weatherStatus = 0;
            if(RedisHandler.isValueInSET(InfraRedisConstants.REDIS_WEATHER_01_SET_NAME,weatherCode))
                weatherStatus = 1;
            else if ((RedisHandler.isValueInSET(InfraRedisConstants.REDIS_WEATHER_02_SET_NAME,weatherCode)))
                weatherStatus = 2;
            else
                weatherStatus = 3;
            Double temp = Double.valueOf(resultJSONObj.getJSONObject(WEATHER_JSON_KEY_NOW)
                    .getString(WEATHER_JSON_KEY_TEMPERATURE));
            weatherNow.setWeatherStatus(weatherStatus);
            weatherNow.setTemperature(temp);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return weatherNow;
    }

    //查询AirNow
    static AirNow queryAirNow(String location){
        AirNow airNow = AirNow.builder().build();
        String url = HTTP_URL_SENIVERSE_AIR_NOW_URL;
        Map<String,Object> params =  new HashMap<>();
        params.put(HTTP_URL_SENIVERSE_LOCATION_KEY,location);
        params.put(HTTP_URL_SENIVERSE_KEY_KEY,HTTP_URL_SENIVERSE_KEY_VALUE);
        params.put(HTTP_URL_SENIVERSE_LANGUAGE_KEY,HTTP_URL_SENIVERSE_LANGUAGE_VALUE);
        params.put(HTTP_URL_SENIVERSE_UNIT_KEY,HTTP_URL_SENIVERSE_UNIT_VALUE);
        params.put(HTTP_URL_SENIVERSE_SCOPE_KEY,HTTP_URL_SENIVERSE_SCOPE_VALUE);
        commonLogInfo("Start queryAirNow @ "+location);
        double aqi = 0.0;
        try{
            String airNowResult = httpGetRequest(url,params);
            commonLogInfo("Get AirNow="+airNowResult);
            JSONObject airNowResultJSONObj = new JSONObject(airNowResult);
            JSONObject resultJSONObj = airNowResultJSONObj.getJSONArray(AIR_JSON_KEY_RESULT)
                    .getJSONObject(AIR_JSON_ARRAY_INDEX);
            String apiValue = resultJSONObj.getJSONObject(AIR_JSON_KEY_AIR)
                    .getJSONObject(AIR_JSON_KEY_CITY).getString(AIR_JSON_KEY_AQI);
            airNow.setAqi(Double.valueOf(apiValue));
        }
        catch(Exception e){
            e.printStackTrace();
            airNow.setAqi(aqi);
        }
        return airNow;
    }

    //整合Weather
    static Weather queryWeather(String location){
        WeatherNow weatherNow = queryWeatherNow(location);
        AirNow airNow = queryAirNow(location);
        Weather weather = Weather.builder()
                .location(location)
                .dateTime(getDateTimeString())
                .weatherNow(weatherNow)
                .airNow(airNow)
                .build();
        return weather;
    }

    static Weather getLastWeatherMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_WEATHER_ZSET),
                Weather.class);
    }

    static Map<String, List<WeatherNow>> getWeatherNowTextAndList(Set<Weather> weatherSet){
        return weatherSet.parallelStream()
                .map(w -> w.getWeatherNow()).collect(Collectors.toList())
                .parallelStream()
                .collect(groupingBy(WeatherNow::getWeatherText, Collectors.toList()));
    }

    static Map<String, Integer> calWeatherNowTextAndNumber(Map<String, List<WeatherNow>> weatherNowMap) {
        return weatherNowMap.entrySet().parallelStream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().size()));
    }

    static String getMostWeatherNowText(Map<String, Integer> weatherTextMap) {
       return  weatherTextMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .findFirst().get().getKey();
    }

    static String getWeatherRecordByPeriod(long startUnixDateTime, long endUnixDateTime){
        Set<String> weatherRedisResult = zRangeByScore(REDIS_WEATHER_ZSET,
                startUnixDateTime, endUnixDateTime);
        Set<Weather> weatherSet = new HashSet<>();
        for (String s : weatherRedisResult) {
            weatherSet.add(transferFromJSON2Object(s, Weather.class));
        }
        return getMostWeatherNowText(calWeatherNowTextAndNumber(getWeatherNowTextAndList(weatherSet)));
    }

    static long setWeatherMessage2Redis(Weather weather){
        return inputValue2ZSET(REDIS_WEATHER_ZSET,transfer2UnixTime(weather.getDateTime()),
                transferFromObject2JSON(weather));
    }
}
