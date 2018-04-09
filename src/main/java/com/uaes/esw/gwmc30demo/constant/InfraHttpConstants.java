package com.uaes.esw.gwmc30demo.constant;

public class InfraHttpConstants {
    public final static String HTTP_CONFIG_CONTENT_TYPE = "application/json;charset=utf-8";
    public final static String HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME = "Access-Control-Allow-Origin";
    public final static String HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE = "*";
    public final static int HTTP_CONFIG_PORT = 6789;
    public final static String HTTP_URL_CHARGE_ON_DEMAND = "/v1/gwm/c30/chargingOnDemand";
    public final static String HTTP_URL_REGISTER = "/v1/gwm/c30/register";
    public final static String HTTP_URL_LOGIN = "/v1/gwm/c30/login";
    public final static String HTTP_URL_LOGOUT = "/v1/gwm/c30/logout";
    public final static String HTTP_URL_QUERY_DRIVINGMODE = "/v1/gwm/c30/queryDrivingMode";
    public final static String HTTP_URL_SET_DEFAULT_DRIVINGMODE = "/v1/gwm/c30/setDefaultDrivingMode";
    public final static String HTTP_URL_SET_CURRENT_DRIVINGMODE = "/v1/gwm/c30/setCurrentDrivingMode";
    public final static String HTTP_URL_SET_CUSTOMER_DRIVINGMODE = "/v1/gwm/c30/setCustomerDrivingMode";
    public final static String HTTP_URL_QUERY_ES_BY_CURRENT = "/v1/gwm/c30/queryESByCurrent";
    public final static String HTTP_URL_QUERY_ES_BY_LAST_CYCLE = "/v1/gwm/c30/queryESByLastCycle";
    public final static String HTTP_URL_QUERY_ES_BY_TODAY = "/v1/gwm/c30/queryESByToday";
    public final static String HTTP_URL_QUERY_ES_BY_THIS_WEEK = "/v1/gwm/c30/queryESByThisWeek";
    public final static String HTTP_URL_QUERY_ES_BY_CUSTOMER = "/v1/gwm/c30/queryESByCustomer";
    public final static String HTTP_URL_START_BATTERY_BALANCE = "/v1/gwm/c30/startBatteryBalance";
    public final static String HTTP_URL_STOP_BATTERY_BALANCE = "/v1/gwm/c30/stopBatteryBalance";

    public final static String HTTP_URL_SENIVERSE_WEATHER_NOW_URL = "https://api.seniverse.com/v3/weather/now.json";
    public final static String HTTP_URL_SENIVERSE_AIR_NOW_URL = "https://api.seniverse.com/v3/air/now.json";
    public final static String HTTP_URL_SENIVERSE_KEY_KEY = "key";
    public final static String HTTP_URL_SENIVERSE_KEY_VALUE = "kv4n4pdseswrqoik";
    public final static String HTTP_URL_SENIVERSE_LANGUAGE_KEY = "language";
    public final static String HTTP_URL_SENIVERSE_LANGUAGE_VALUE = "en";
    public final static String HTTP_URL_SENIVERSE_UNIT_KEY = "unit";
    public final static String HTTP_URL_SENIVERSE_UNIT_VALUE = "c";
    public final static String HTTP_URL_SENIVERSE_LOCATION_KEY = "location";
    public final static int HTTP_URL_SENIVERSE_QUERY_INTERVAL_MINUTES= 5;
    public final static String HTTP_URL_SENIVERSE_SCOPE_KEY = "scope";
    public final static String HTTP_URL_SENIVERSE_SCOPE_VALUE = "all";
}
