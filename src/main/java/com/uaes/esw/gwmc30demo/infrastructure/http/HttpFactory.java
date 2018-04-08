package com.uaes.esw.gwmc30demo.infrastructure.http;

import com.uaes.esw.gwmc30demo.infrastructure.websocket.WebSocketHandler;
import spark.Spark;

import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_CONFIG_PORT;
import static com.uaes.esw.gwmc30demo.constant.InfraWebSocketConstants.WEBSOCKET_URL_ENERGY_SAVING_REMIND;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpHandler.setRouter;
import static spark.Spark.init;
import static spark.Spark.webSocket;

public class HttpFactory {
    public static void setHttpServerProperties(int port){
        Spark.port(port);
    }

    public static void startApplicationService(){
        //webSocket(WEBSOCKET_URL_ENERGY_SAVING_REMIND, WebSocketHandler.class);
        setHttpServerProperties(HTTP_CONFIG_PORT);
        setRouter();
    }

    public static void main(String[] args) {
        startApplicationService();
    }
}
