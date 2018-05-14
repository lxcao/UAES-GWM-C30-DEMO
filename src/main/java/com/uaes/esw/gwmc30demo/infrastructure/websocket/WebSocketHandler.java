package com.uaes.esw.gwmc30demo.infrastructure.websocket;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;

import static com.uaes.esw.gwmc30demo.domain.service.BatteryDomainService.createBatteryBalanceNotice;
import static com.uaes.esw.gwmc30demo.domain.service.BatteryDomainService.createBatteryStatusNotice;
import static com.uaes.esw.gwmc30demo.domain.service.EnergySavingDomainService.createESRemind;
import static com.uaes.esw.gwmc30demo.domain.service.VehicleDomainService.createVehicleStatusNotice;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.websocketLogInfo;

@WebSocket
public class WebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String username = user.getRemoteAddress().getHostName()
                +":"+user.getRemoteAddress().getPort()
                +"@"+ LocalDateTime.now().toString();
        WebSocketFactory.userUserNameMap.put(user, username);
        websocketLogInfo(username + " is connected");
        websocketLogInfo("There are " + WebSocketFactory.userUserNameMap.size() + " users now");
        //send out once connected
        sendStrMessage(transferFromObject2JSON(createVehicleStatusNotice()));
        sendStrMessage(transferFromObject2JSON(createESRemind()));
        sendStrMessage(transferFromObject2JSON(createBatteryStatusNotice()));
        sendStrMessage(transferFromObject2JSON(createBatteryBalanceNotice()));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = WebSocketFactory.userUserNameMap.get(user);
        WebSocketFactory.userUserNameMap.remove(user);
        websocketLogInfo(username + " is closed");
        websocketLogInfo("There are " + WebSocketFactory.userUserNameMap.size() + " users now");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        //websocketLogInfo("Received " + WebSocketFactory.userUserNameMap.get(user) + "with message: " + message);
    }

    //Send string message to all user
    public static void sendStrMessage(String msg){
        websocketLogInfo(msg);
        WebSocketFactory.userUserNameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try{
                session.getRemote().sendString(msg);
                websocketLogInfo("Send to : "+session.getRemoteAddress().getHostName());
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    //Send byte message to all user
    public void sendByteMessage(ByteBuffer byteMsg){

        WebSocketFactory.userUserNameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try{
                session.getRemote().sendBytes(byteMsg);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
}

