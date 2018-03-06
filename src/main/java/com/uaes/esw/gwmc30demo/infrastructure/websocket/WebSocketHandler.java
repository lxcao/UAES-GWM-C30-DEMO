package com.uaes.esw.gwmc30demo.infrastructure.websocket;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;

@WebSocket
public class WebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String username = user.getRemoteAddress().getHostName()
                +":"+user.getRemoteAddress().getPort()
                +"@"+ LocalDateTime.now().toString();
        WebSocketFactory.userUserNameMap.put(user, username);
        System.out.println(username + " is connected");
        System.out.println("There are " + WebSocketFactory.userUserNameMap.size() + " users now");
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = WebSocketFactory.userUserNameMap.get(user);
        WebSocketFactory.userUserNameMap.remove(user);
        System.out.println(username + " is closed");
        System.out.println("There are " + WebSocketFactory.userUserNameMap.size() + " users now");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        System.out.println("Received " + WebSocketFactory.userUserNameMap.get(user) + "with message: " + message);
    }

    //Send string message to all user
    public void sendStrMessage(String msg){

        WebSocketFactory.userUserNameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try{
                //System.out.println(LocalDateTime.now().toString()+" "+msg);
                session.getRemote().sendString(msg);

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

