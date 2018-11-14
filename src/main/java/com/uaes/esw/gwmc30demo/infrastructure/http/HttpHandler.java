package com.uaes.esw.gwmc30demo.infrastructure.http;

import spark.Spark;

import static com.uaes.esw.gwmc30demo.application.service.ChargingOnDemand.chargingOnDemandByJourneyAssembler;
import static com.uaes.esw.gwmc30demo.application.service.DriverLogIn.driverLogin;
import static com.uaes.esw.gwmc30demo.application.service.DriverLogOut.driverLogout;
import static com.uaes.esw.gwmc30demo.application.service.DriverRegister.driverRegister;
import static com.uaes.esw.gwmc30demo.application.service.QueryDrivingCycleByDate.queryDrivingCycleByDate;
import static com.uaes.esw.gwmc30demo.application.service.QueryDrivingStyle.queryDrivingStyle;
import static com.uaes.esw.gwmc30demo.application.service.QueryEnergySavingByCurrent.queryESByCurrent;
import static com.uaes.esw.gwmc30demo.application.service.QueryEnergySavingByCustomer.queryESByCustomer;
import static com.uaes.esw.gwmc30demo.application.service.QueryEnergySavingByLastCycle.queryESByLastCycle;
import static com.uaes.esw.gwmc30demo.application.service.QueryEnergySavingByThisWeek.queryESByThisWeek;
import static com.uaes.esw.gwmc30demo.application.service.QueryEnergySavingByToday.queryESByToday;
import static com.uaes.esw.gwmc30demo.application.service.SetCurrentDrivingStyle.setCurrentDrivingStyle;
import static com.uaes.esw.gwmc30demo.application.service.SetCustomerDrivingStyle.setCustomerDrivingStyle;
import static com.uaes.esw.gwmc30demo.application.service.SetDefaultDrivingStyle.setDefaultDrivingStyle;
import static com.uaes.esw.gwmc30demo.application.service.SetSpeedAuxiliary.setSpeedAuxiliary;
import static com.uaes.esw.gwmc30demo.application.service.StartBatteryBalance.startBatteryBalance;
import static com.uaes.esw.gwmc30demo.application.service.StopBatteryBalance.stopBatteryBalance;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.*;
import static com.uaes.esw.gwmc30demo.constant.InfraLog4JConstants.LOG4J_INPUT;
import static com.uaes.esw.gwmc30demo.constant.InfraLog4JConstants.LOG4J_OUTPUT;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.*;

public class HttpHandler {

    public static void setRouter(){
        Spark.post(HTTP_URL_CHARGE_ON_DEMAND,(req, res) -> {
            chargingOnDemandLogInfo(HTTP_URL_CHARGE_ON_DEMAND);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            chargingOnDemandLogInfo(LOG4J_INPUT+req.body());
            String resString = chargingOnDemandByJourneyAssembler(req.body());
            chargingOnDemandLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_REGISTER,(req, res) -> {
            loginLogInfo(HTTP_URL_REGISTER);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            loginLogInfo(LOG4J_INPUT+req.body());
            String resString = driverRegister(req.body());
            loginLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_LOGIN,(req, res) -> {
            loginLogInfo(HTTP_URL_LOGIN);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            loginLogInfo(LOG4J_INPUT+req.body());
            String resString = driverLogin(req.body());
            loginLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_LOGOUT,(req, res) -> {
            loginLogInfo(HTTP_URL_LOGOUT);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            loginLogInfo(LOG4J_INPUT+req.body());
            String resString = driverLogout(req.body());
            loginLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_QUERY_DRIVINGMODE,(req, res) -> {
            drivingModelLogInfo(HTTP_URL_QUERY_DRIVINGMODE);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            drivingModelLogInfo(LOG4J_INPUT+req.body());
            String resString = queryDrivingStyle(req.body());
            drivingModelLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_SET_DEFAULT_DRIVINGMODE,(req, res) -> {
            drivingModelLogInfo(HTTP_URL_SET_DEFAULT_DRIVINGMODE);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            drivingModelLogInfo(LOG4J_INPUT+req.body());
            String resString = setDefaultDrivingStyle(req.body());
            drivingModelLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_SET_CURRENT_DRIVINGMODE,(req, res) -> {
            drivingModelLogInfo(HTTP_URL_SET_CURRENT_DRIVINGMODE);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            drivingModelLogInfo(LOG4J_INPUT+req.body());
            String resString = setCurrentDrivingStyle(req.body());
            drivingModelLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_SET_CUSTOMER_DRIVINGMODE,(req, res) -> {
            drivingModelLogInfo(HTTP_URL_SET_CUSTOMER_DRIVINGMODE);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            drivingModelLogInfo(LOG4J_INPUT+req.body());
            String resString = setCustomerDrivingStyle(req.body());
            drivingModelLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_QUERY_ES_BY_CURRENT,(req, res) -> {
            energySavingLogInfo(HTTP_URL_QUERY_ES_BY_CURRENT);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            energySavingLogInfo(LOG4J_INPUT+req.body());
            String resString = queryESByCurrent(req.body());
            energySavingLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_QUERY_ES_BY_LAST_CYCLE,(req, res) -> {
            energySavingLogInfo(HTTP_URL_QUERY_ES_BY_LAST_CYCLE);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            energySavingLogInfo(LOG4J_INPUT+req.body());
            String resString = queryESByLastCycle(req.body());
            energySavingLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_QUERY_ES_BY_TODAY,(req, res) -> {
            energySavingLogInfo(HTTP_URL_QUERY_ES_BY_TODAY);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            energySavingLogInfo(LOG4J_INPUT+req.body());
            String resString = queryESByToday(req.body());
            energySavingLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_QUERY_ES_BY_THIS_WEEK,(req, res) -> {
            energySavingLogInfo(HTTP_URL_QUERY_ES_BY_THIS_WEEK);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            energySavingLogInfo(LOG4J_INPUT+req.body());
            String resString = queryESByThisWeek(req.body());
            energySavingLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_QUERY_ES_BY_CUSTOMER,(req, res) -> {
            energySavingLogInfo(HTTP_URL_QUERY_ES_BY_CUSTOMER);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            energySavingLogInfo(LOG4J_INPUT+req.body());
            String resString = queryESByCustomer(req.body());
            energySavingLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_START_BATTERY_BALANCE,(req, res) -> {
            batteryBalanceLogInfo(HTTP_URL_START_BATTERY_BALANCE);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            batteryBalanceLogInfo(LOG4J_INPUT+req.body());
            String resString = startBatteryBalance(req.body());
            batteryBalanceLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_STOP_BATTERY_BALANCE,(req, res) -> {
            batteryBalanceLogInfo(HTTP_URL_STOP_BATTERY_BALANCE);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            batteryBalanceLogInfo(LOG4J_INPUT+req.body());
            String resString = stopBatteryBalance(req.body());
            batteryBalanceLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_SET_SPEED_AUXILIARY,(req, res) -> {
            speedAuxLogInfo(HTTP_URL_SET_SPEED_AUXILIARY);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            speedAuxLogInfo(LOG4J_INPUT+req.body());
            String resString = setSpeedAuxiliary(req.body());
            speedAuxLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
        Spark.post(HTTP_URL_QUERY_DRIVING_CYCLE_BY_DATE,(req, res) -> {
            blackBoxLogInfo(HTTP_URL_QUERY_DRIVING_CYCLE_BY_DATE);
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME,
                    HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            blackBoxLogInfo(LOG4J_INPUT+req.body());
            String resString = queryDrivingCycleByDate(req.body());
            blackBoxLogInfo(LOG4J_OUTPUT+resString);
            return resString;
        });
    }
}
