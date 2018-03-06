package com.uaes.esw.gwmc30demo.infrastructure.http;

import spark.Spark;

import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_CONFIG_PORT;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpHandler.setRouter;

public class HttpFactory {
    public static void setHttpServerProperties(int port){
        Spark.port(port);
    }

    public static void main(String[] args) {
        setHttpServerProperties(HTTP_CONFIG_PORT);
        setRouter();
    }
}
