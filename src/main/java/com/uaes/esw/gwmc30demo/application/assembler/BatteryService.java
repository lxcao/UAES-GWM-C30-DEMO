package com.uaes.esw.gwmc30demo.application.assembler;

import com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus.StartBatteryBalanceReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus.StartBatteryBalanceRes;
import com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus.StopBatteryBalanceReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus.StopBatteryBalanceRes;
import com.uaes.esw.gwmc30demo.domain.service.LogInDomainService;
import com.uaes.esw.gwmc30demo.infrastructure.websocket.WebSocketHandler;

import static com.uaes.esw.gwmc30demo.domain.service.BatteryDomainService.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface BatteryService {
    static void sendOutBatteryStatusNotice(){
        WebSocketHandler.sendStrMessage(transferFromObject2JSON(createBatteryStatusNotice()));
    }

    static void sendOutBatteryBalanceNotice(){
        WebSocketHandler.sendStrMessage(transferFromObject2JSON(createBatteryBalanceNotice()));
    }

    static String startPackBatteryBalance(String startBBReq){
        StartBatteryBalanceReq sbbReq = transferFromJSON2Object(startBBReq, StartBatteryBalanceReq.class);
        if(LogInDomainService.notLogInDomainService(sbbReq.getDriver(),sbbReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(sbbReq.getDriver(),sbbReq.getDateTime());
        StartBatteryBalanceRes sbbRes =startBBDomainService(sbbReq);
        return transferFromObject2JSON(sbbRes);
    }

    static String stopPackBatteryBalance(String stopBBReq){
        StopBatteryBalanceReq sbbReq = transferFromJSON2Object(stopBBReq, StopBatteryBalanceReq.class);
        if(LogInDomainService.notLogInDomainService(sbbReq.getDriver(),sbbReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(sbbReq.getDriver(),sbbReq.getDateTime());
        StopBatteryBalanceRes sbbRes =stopBBDomainService(sbbReq);
        return transferFromObject2JSON(sbbRes);
    }

}
