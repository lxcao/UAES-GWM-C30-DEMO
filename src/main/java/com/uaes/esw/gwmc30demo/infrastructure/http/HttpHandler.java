package com.uaes.esw.gwmc30demo.infrastructure.http;

import spark.Spark;

import java.time.LocalDateTime;

import static com.uaes.esw.gwmc30demo.application.assembler.ChargingOnDemand.chargingOnDemandByJourneyAssembler;
import static com.uaes.esw.gwmc30demo.application.assembler.DriverLogIn.driverLogin;
import static com.uaes.esw.gwmc30demo.application.assembler.DriverLogOut.driverLogout;
import static com.uaes.esw.gwmc30demo.application.assembler.DriverRegister.driverRegister;
import static com.uaes.esw.gwmc30demo.application.assembler.QueryDrivingStyle.queryDrivingStyle;
import static com.uaes.esw.gwmc30demo.application.assembler.SetCurrentDrivingStyle.setCurrentDrivingStyle;
import static com.uaes.esw.gwmc30demo.application.assembler.SetDefaultDrivingStyle.setDefaultDrivingStyle;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.*;

public class HttpHandler {

    private static void debugPrintInput(String url, String input){
        System.out.println(url+"@"+ LocalDateTime.now());
        System.out.println("Input="+input);
    }

    private static void debugPrintOutput(String output){
        System.out.println("Output="+output);
        System.out.println();
    }

    public static void setRouter(){
        Spark.post(HTTP_URL_CHARGE_ON_DEMAND,(req, res) -> {
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME, HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            debugPrintInput(HTTP_URL_CHARGE_ON_DEMAND, req.body());
            String resString = chargingOnDemandByJourneyAssembler(req.body());
            debugPrintOutput(resString);
            return resString;
        });
        Spark.post(HTTP_URL_REGISTER,(req, res) -> {
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME, HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            debugPrintInput(HTTP_URL_REGISTER, req.body());
            String resString = driverRegister(req.body());
            debugPrintOutput(resString);
            return resString;
        });
        Spark.post(HTTP_URL_LOGIN,(req, res) -> {
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME, HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            debugPrintInput(HTTP_URL_LOGIN, req.body());
            String resString = driverLogin(req.body());
            debugPrintOutput(resString);
            return resString;
        });
        Spark.post(HTTP_URL_LOGOUT,(req, res) -> {
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME, HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            debugPrintInput(HTTP_URL_LOGOUT, req.body());
            String resString = driverLogout(req.body());
            debugPrintOutput(resString);
            return resString;
        });
        Spark.post(HTTP_URL_QUERY_DRIVINGMODE,(req, res) -> {
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME, HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            debugPrintInput(HTTP_URL_QUERY_DRIVINGMODE, req.body());
            String resString = queryDrivingStyle(req.body());
            debugPrintOutput(resString);
            return resString;
        });
        Spark.post(HTTP_URL_SET_DEFAULT_DRIVINGMODE,(req, res) -> {
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME, HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            debugPrintInput(HTTP_URL_SET_DEFAULT_DRIVINGMODE, req.body());
            String resString = setDefaultDrivingStyle(req.body());
            debugPrintOutput(resString);
            return resString;
        });
        Spark.post(HTTP_URL_SET_CURRENT_DRIVINGMODE,(req, res) -> {
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME, HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            debugPrintInput(HTTP_URL_SET_CURRENT_DRIVINGMODE, req.body());
            String resString = setCurrentDrivingStyle(req.body());
            debugPrintOutput(resString);
            return resString;
        });
    }
}
