package com.uaes.esw.gwmc30demo.infrastructure.utils;

import org.apache.log4j.Logger;

import static com.uaes.esw.gwmc30demo.constant.InfraLog4JConstants.*;

public class LoggerUtils {

    private static final Logger common = Logger.getLogger(LOG4J_COMMON);
    private static final Logger login = Logger.getLogger(LOG4J_LOGIN);
    private static final Logger drivingModel = Logger.getLogger(LOG4J_DRIVINGMODEL);
    private static final Logger energySaving = Logger.getLogger(LOG4J_ENERGYSAVING);
    private static final Logger chargingOnDemand = Logger.getLogger(LOG4J_CHARGINGONDEMAND);
    private static final Logger batteryBalance = Logger.getLogger(LOG4J_BATTERYBALANCE);
    private static final Logger batteryStatus = Logger.getLogger(LOG4J_BATTERYSTATUS);
    private static final Logger websocket = Logger.getLogger(LOG4J_WEBSOCKET);
    private static final Logger vehicle = Logger.getLogger(LOG4J_VEHICLE);
    private static final Logger speedAux = Logger.getLogger(LOG4J_SPDAUX);
    private static final Logger blackBox = Logger.getLogger(LOG4J_BLACKBOX);

    public static void commonLogInfo(String log){
        common.info(log+LOG4J_RETURN);
    }

    public static void loginLogInfo(String log){
        login.info(log+LOG4J_RETURN);
    }

    public static void drivingModelLogInfo(String log){
        drivingModel.info(log+LOG4J_RETURN);
    }

    public static void energySavingLogInfo(String log){
        energySaving.info(log+LOG4J_RETURN);
    }

    public static void chargingOnDemandLogInfo(String log){
        chargingOnDemand.info(log+LOG4J_RETURN);
    }

    public static void batteryBalanceLogInfo(String log){
        batteryBalance.info(log+LOG4J_RETURN);
    }

    public static void batteryStatusLogInfo(String log){
        batteryStatus.info(log+LOG4J_RETURN);
    }

    public static void websocketLogInfo(String log){
        websocket.info(log+LOG4J_RETURN);
    }

    public static void vehicleLogInfo(String log){
        vehicle.info(log+LOG4J_RETURN);
    }

    public static void speedAuxLogInfo(String log){
        speedAux.info(log+LOG4J_RETURN);
    }

    public static void blackBoxLogInfo(String log){
        blackBox.info(log+LOG4J_RETURN);
    }

}
