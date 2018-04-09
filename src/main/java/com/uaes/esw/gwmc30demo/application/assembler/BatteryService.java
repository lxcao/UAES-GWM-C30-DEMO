package com.uaes.esw.gwmc30demo.application.assembler;

import com.uaes.esw.gwmc30demo.infrastructure.websocket.WebSocketHandler;

import static com.uaes.esw.gwmc30demo.domain.service.BatteryDomainService.createBatteryStatus;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface BatteryService {
    static void sendOutBatteryStatusNotice(){
        WebSocketHandler.sendStrMessage(transferFromObject2JSON(createBatteryStatus()));

    }
}
