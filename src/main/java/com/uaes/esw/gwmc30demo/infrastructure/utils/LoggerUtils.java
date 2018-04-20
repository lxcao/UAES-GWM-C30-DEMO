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

}
