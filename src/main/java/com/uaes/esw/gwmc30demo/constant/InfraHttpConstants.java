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
}
