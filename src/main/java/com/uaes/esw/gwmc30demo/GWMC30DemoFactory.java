package com.uaes.esw.gwmc30demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.sendOutRemindNotice;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_CONFIG_PORT;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_URL_SENIVERSE_QUERY_INTERVAL_MINUTES;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_VEHICLE_HASH_NAME;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS;
import static com.uaes.esw.gwmc30demo.constant.InfraWebSocketConstants.WEBSOCKET_ENERGY_SAVING_REMIND_INTERVAL_SECONDS;
import static com.uaes.esw.gwmc30demo.constant.InfraWebSocketConstants.WEBSOCKET_PORT;
import static com.uaes.esw.gwmc30demo.constant.InfraWebSocketConstants.WEBSOCKET_URL_ENERGY_SAVING_REMIND;
import static com.uaes.esw.gwmc30demo.constant.WeatherConstants.WEATHER_LOCATION;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.updateVehicleSnapShot;
import static com.uaes.esw.gwmc30demo.domain.service.UpdateWeather2VehicleDomainService.updateWeather2VehicleDomainService;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpFactory.setHttpServerProperties;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpHandler.setRouter;
import static com.uaes.esw.gwmc30demo.infrastructure.websocket.WebSocketFactory.setWebSocketProperties;

public class GWMC30DemoFactory {
    //开始Restful服务
    public static void startRestfulService(){
        setWebSocketProperties(WEBSOCKET_URL_ENERGY_SAVING_REMIND);
        setHttpServerProperties(HTTP_CONFIG_PORT);
        setRouter();

    }

    //开始WebSocket服务
    public static void startPushMessageService(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while(true){
                sendOutRemindNotice();
                try{
                    TimeUnit.SECONDS.sleep(WEBSOCKET_ENERGY_SAVING_REMIND_INTERVAL_SECONDS);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    //每500毫秒轮询并更新Vehicle Hash
    static void updateVehicleSnapShotManager(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while(true){
                updateVehicleSnapShot(REDIS_VEHICLE_HASH_NAME);
                try{
                    TimeUnit.MILLISECONDS.sleep(REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //每隔5分钟查询天气，缺省是上海 shanghai
    static void queryWeatherManager(String location){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while(true){
                updateWeather2VehicleDomainService(location);
                try{
                    TimeUnit.MINUTES.sleep(HTTP_URL_SENIVERSE_QUERY_INTERVAL_MINUTES);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //开始和车辆相关的数据操作
    static void startVehicleDataService(){
        updateVehicleSnapShotManager();
    }

    public static void main(String[] args) {
        startVehicleDataService();
        queryWeatherManager(WEATHER_LOCATION);
        startRestfulService();
        startPushMessageService();

    }
}
