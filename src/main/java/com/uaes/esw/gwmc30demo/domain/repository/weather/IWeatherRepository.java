package com.uaes.esw.gwmc30demo.domain.repository.weather;

import com.uaes.esw.gwmc30demo.constant.InfraRedisConstants;
import com.uaes.esw.gwmc30demo.domain.model.entity.weather.Weather;
import com.uaes.esw.gwmc30demo.infrastructure.http.HttpClientUtil;
import com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.*;
import static com.uaes.esw.gwmc30demo.constant.WeatherConstants.*;

public interface IWeatherRepository {
    //查询WeatherNow
    static Weather queryWeatherNow(String location){
        Weather weatherNow = Weather.builder().build();
        String url = HTTP_URL_SENIVERSE_WEATHER_NOW_URL;
        Map<String,Object> params =  new HashMap<>();
        params.put(HTTP_URL_SENIVERSE_LOCATION_KEY,location);
        params.put(HTTP_URL_SENIVERSE_KEY_KEY,HTTP_URL_SENIVERSE_KEY_VALUE);
        params.put(HTTP_URL_SENIVERSE_LANGUAGE_KEY,HTTP_URL_SENIVERSE_LANGUAGE_VALUE);
        params.put(HTTP_URL_SENIVERSE_UNIT_KEY,HTTP_URL_SENIVERSE_UNIT_VALUE);
        try{
            String weatherResult = HttpClientUtil.httpGetRequest(url,params);
            JSONObject weatherResultJSONObj = new JSONObject(weatherResult);
            JSONObject resultJSONObj = weatherResultJSONObj.getJSONArray(WEATHER_JSON_KEY_RESULT)
                    .getJSONObject(WEATHER_JSON_ARRAY_INDEX);
            String loc = resultJSONObj.getJSONObject(WEATHER_JSON_KEY_LOCATION)
                    .getString(WEATHER_JSON_KEY_NAME);
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
            weatherNow.setLocation(loc);
            weatherNow.setWeatherStatus(weatherStatus);
            weatherNow.setTemperature(temp);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return weatherNow;
    }

    //查询AirNow
    static Weather queryAirNow(String location){
        Weather airNow = Weather.builder().build();
        String url = HTTP_URL_SENIVERSE_AIR_NOW_URL;
        Map<String,Object> params =  new HashMap<>();
        params.put(HTTP_URL_SENIVERSE_LOCATION_KEY,location);
        params.put(HTTP_URL_SENIVERSE_KEY_KEY,HTTP_URL_SENIVERSE_KEY_VALUE);
        params.put(HTTP_URL_SENIVERSE_LANGUAGE_KEY,HTTP_URL_SENIVERSE_LANGUAGE_VALUE);
        params.put(HTTP_URL_SENIVERSE_UNIT_KEY,HTTP_URL_SENIVERSE_UNIT_VALUE);
        params.put(HTTP_URL_SENIVERSE_SCOPE_KEY,HTTP_URL_SENIVERSE_SCOPE_VALUE);
        try{
            String airNowResult = HttpClientUtil.httpGetRequest(url,params);
            JSONObject airNowResultJSONObj = new JSONObject(airNowResult);
            //TODO: get aqi from JSON
            airNow.setAqi(0.0);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return airNow;
    }

    //查询AirNow
    static Weather queryWeather(String location){
        Weather weatherNow = queryWeatherNow(location);
        Weather airNow = queryAirNow(location);
        Weather weather = Weather.builder()
                .weatherStatus(weatherNow.getWeatherStatus())
                .temperature(weatherNow.getTemperature())
                .location(location)
                .aqi(airNow.getAqi())
                .build();
        return weather;
    }
}
