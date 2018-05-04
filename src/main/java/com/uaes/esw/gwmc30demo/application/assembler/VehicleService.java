package com.uaes.esw.gwmc30demo.application.assembler;

import com.uaes.esw.gwmc30demo.infrastructure.websocket.WebSocketHandler;

import static com.uaes.esw.gwmc30demo.domain.service.VehicleDomainService.createVehicleStatusNotice;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface VehicleService {
    static void sendOutFrontPageNotice(){
        WebSocketHandler.sendStrMessage(transferFromObject2JSON(createVehicleStatusNotice()));
    }
}
