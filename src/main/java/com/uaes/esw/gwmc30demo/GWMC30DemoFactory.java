package com.uaes.esw.gwmc30demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.uaes.esw.gwmc30demo.application.assembler.BatteryService.sendOutBatteryBalanceNotice;
import static com.uaes.esw.gwmc30demo.application.assembler.BatteryService.sendOutBatteryStatusNotice;
import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.sendOutEnergySavingCurrentCycleNotice;
import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.sendOutEnergySavingRemindNotice;
import static com.uaes.esw.gwmc30demo.application.assembler.VehicleService.sendOutFrontPageNotice;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_CONFIG_PORT;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_URL_SENIVERSE_QUERY_INTERVAL_MINUTES;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_VEHICLE_HASH_NAME;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS;
import static com.uaes.esw.gwmc30demo.constant.InfraWebSocketConstants.WEBSOCKET_BATTERY_STATUS_INTERVAL_SECONDS;
import static com.uaes.esw.gwmc30demo.constant.InfraWebSocketConstants.WEBSOCKET_ENERGY_SAVING_REMIND_INTERVAL_SECONDS;
import static com.uaes.esw.gwmc30demo.constant.InfraWebSocketConstants.WEBSOCKET_URL_ENERGY_SAVING_REMIND;
import static com.uaes.esw.gwmc30demo.constant.WeatherConstants.WEATHER_LOCATION;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.updateVehicleSnapShot;
import static com.uaes.esw.gwmc30demo.domain.service.UpdateWeather2VehicleDomainService.updateWeather2VehicleDomainService;
import static com.uaes.esw.gwmc30demo.domain.service.VehicleDomainService.dealStaffWhenPowerOff;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpFactory.setHttpServerProperties;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpHandler.setRouter;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.sleepMilliSeconds;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.sleepMinutes;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.sleepSeconds;
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
        executor.execute(() -> {
            while(true){
                    sleepSeconds(WEBSOCKET_ENERGY_SAVING_REMIND_INTERVAL_SECONDS);
                    //节能之当前驾驶信息
                    sendOutEnergySavingCurrentCycleNotice();
                    sleepSeconds(WEBSOCKET_ENERGY_SAVING_REMIND_INTERVAL_SECONDS);
                    //节能提醒空调开窗
                    sendOutEnergySavingRemindNotice();
                    sleepSeconds(WEBSOCKET_ENERGY_SAVING_REMIND_INTERVAL_SECONDS);
                    //正在均衡的状态和如果均衡的效果
                    sendOutBatteryBalanceNotice();
            }
        });
        //2秒发送一次电池状态和首页状态
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        executor1.execute(() -> {
            while(true){
                //首页
                sendOutFrontPageNotice();
                sleepSeconds(WEBSOCKET_BATTERY_STATUS_INTERVAL_SECONDS);
                //电池状态
                sendOutBatteryStatusNotice();
                sleepSeconds(WEBSOCKET_BATTERY_STATUS_INTERVAL_SECONDS);
            }
        });
    }

    //每500毫秒轮询并更新Vehicle Hash
    static void updateVehicleSnapShotManager(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while(true){
                //Vehicle SnapShot
                updateVehicleSnapShot(REDIS_VEHICLE_HASH_NAME);
                sleepMilliSeconds(REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS);
            }
        });
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        executor1.execute(() -> {
            while(true){
                //断电后触发驾驶循环和重置驾驶模式
                dealStaffWhenPowerOff();
                sleepMilliSeconds(REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS);
            }
        });
    }

    //每隔5分钟查询天气，缺省是上海 shanghai
    static void queryWeatherManager(String location){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while(true){
                updateWeather2VehicleDomainService(location);
                sleepMinutes(HTTP_URL_SENIVERSE_QUERY_INTERVAL_MINUTES);
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
