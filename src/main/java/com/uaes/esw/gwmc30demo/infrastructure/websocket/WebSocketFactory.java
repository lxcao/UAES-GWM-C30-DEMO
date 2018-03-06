package com.uaes.esw.gwmc30demo.infrastructure.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.util.concurrent.*;

import static com.uaes.esw.gwmc30demo.constant.InfraWebSocketConstants.*;
import static spark.Spark.*;

public class WebSocketFactory {

    private static int monitorInterval = REDIS_CONFIG_MONITOR_INTERVAL;

    static ConcurrentMap<Session, String> userUserNameMap = new ConcurrentHashMap<>();

    //how many clients opened
    public static void calClientsNumber(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while(true){
                System.out.println("There are "+userUserNameMap.keySet().stream().filter(Session::isOpen).count()+" Clients");
                try {
                    TimeUnit.SECONDS.sleep(monitorInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public static void start(String path, int port){
        webSocket(path, WebSocketHandler.class);
        //cannot set -1
        webSocketIdleTimeoutMillis(Integer.MAX_VALUE);
        port(port);
        init();
        calClientsNumber();
    }

    public static void todoBusiness(){
        //for business logic
    }

    public static void main(String[] args) {

        start("/simulatecanmessage",6767);
        todoBusiness();

    }
}
