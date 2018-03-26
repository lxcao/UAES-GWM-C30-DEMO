package com.uaes.esw.gwmc30demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_CONFIG_PORT;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_URL_SENIVERSE_QUERY_INTERVAL_SECONDS;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_VEHICLE_HASH_NAME;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS;
import static com.uaes.esw.gwmc30demo.constant.WeatherConstants.WEATHER_LOCATION;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.updateVehicleSnapShot;
import static com.uaes.esw.gwmc30demo.domain.service.UpdateWeatherNow2VehicleDomainService.updateWeatherNow2VehicleDomainService;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpFactory.setHttpServerProperties;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpHandler.setRouter;

public class GWMC30DemoFactory {
    //开始Restful服务
    public static void startApplicationService(){
        setHttpServerProperties(HTTP_CONFIG_PORT);
        setRouter();
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

    //每隔5秒查询天气，缺省是上海 shanghai
    static void queryWeatherNowManager(String location){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while(true){
                //TODO: queryandSendWeather2Vehicle
                updateWeatherNow2VehicleDomainService(location);
                try{
                    TimeUnit.SECONDS.sleep(HTTP_URL_SENIVERSE_QUERY_INTERVAL_SECONDS);
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
        startApplicationService();
        queryWeatherNowManager(WEATHER_LOCATION);
    }
}
