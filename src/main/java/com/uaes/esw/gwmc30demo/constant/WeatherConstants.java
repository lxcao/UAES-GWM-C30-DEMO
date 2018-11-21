package com.uaes.esw.gwmc30demo.constant;

public class WeatherConstants {

    public final static String WEATHER_LOCATION = "shanghai";
    public final static String WEATHER_JSON_KEY_RESULT = "results";
    public final static int WEATHER_JSON_ARRAY_INDEX = 0;
    public final static String WEATHER_JSON_KEY_LOCATION = "location";
    public final static String WEATHER_JSON_KEY_NAME = "name";
    public final static String WEATHER_JSON_KEY_NOW = "now";
    public final static String WEATHER_JSON_KEY_TEXT = "text";
    public final static String WEATHER_JSON_KEY_CODE = "code";
    public final static String WEATHER_JSON_KEY_TEMPERATURE = "temperature";

    public final static String AIR_JSON_KEY_RESULT = "results";
    public final static int AIR_JSON_ARRAY_INDEX = 0;
    public final static String AIR_JSON_KEY_AIR = "air";
    public final static String AIR_JSON_KEY_CITY = "city";
    public final static String AIR_JSON_KEY_AQI = "aqi";

    public final static String WEATHER_CODE_SUNNY = "0"; //晴
    public final static String WEATHER_CODE_CLEAR = "1"; //晴
    public final static String WEATHER_CODE_FAIR = "2"; //晴
    public final static String WEATHER_CODE_FAIR_DARK = "3"; //晴
    public final static String WEATHER_CODE_FAIR_CLOUDY = "4"; //多云
    //TODO: add more if needed
    public final static String WEATHER_CODE_FAIR_UNKNOWN = "99"; //未知
}
