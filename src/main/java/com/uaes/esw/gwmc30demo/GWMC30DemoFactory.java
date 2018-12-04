package com.uaes.esw.gwmc30demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.uaes.esw.gwmc30demo.application.assembler.BatteryService.sendOutBatteryBalanceNotice;
import static com.uaes.esw.gwmc30demo.application.assembler.BatteryService.sendOutBatteryStatusNotice;
import static com.uaes.esw.gwmc30demo.application.assembler.BatteryService.sendOutCSCVCellNotice;
import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.sendOutEnergySavingCurrentCycleNotice;
import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.sendOutEnergySavingRemindNotice;
import static com.uaes.esw.gwmc30demo.application.assembler.SpeedAuxiliaryService.sendOutSpeedAuxStatusNotice;
import static com.uaes.esw.gwmc30demo.application.assembler.VehicleService.sendOutFrontPageNotice;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.WAITING_INTERVAL_SECONDS;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_CONFIG_PORT;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.HTTP_URL_SENIVERSE_QUERY_INTERVAL_MINUTES;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_VEHICLE_HASH_NAME;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS;
import static com.uaes.esw.gwmc30demo.constant.InfraWebSocketConstants.*;
import static com.uaes.esw.gwmc30demo.constant.WeatherConstants.WEATHER_LOCATION;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.updateVehicleSnapShot;
import static com.uaes.esw.gwmc30demo.domain.service.UpdateWeather2VehicleDomainService.updateWeather2VehicleDomainService;
import static com.uaes.esw.gwmc30demo.domain.service.VehicleDomainService.dealStaffWhenPowerChanged;
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
        sleepSeconds(WAITING_INTERVAL_SECONDS);
    }

    //开始WebSocket服务
    public static void startPushMessageService(){
        //每10秒发送一次节能状态
        ExecutorService executorES = Executors.newSingleThreadExecutor();
        executorES.execute(() -> {
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
        //每2秒发送一次电池状态和首页状态
        ExecutorService executorST = Executors.newSingleThreadExecutor();
        executorST.execute(() -> {
            while(true){
                //首页
                sendOutFrontPageNotice();
                sleepSeconds(WEBSOCKET_BATTERY_STATUS_INTERVAL_SECONDS);
                //电池状态
                sendOutBatteryStatusNotice();
                sleepSeconds(WEBSOCKET_BATTERY_STATUS_INTERVAL_SECONDS);
            }
        });
        //每5秒发送一次车速辅助状态
        ExecutorService executorSA = Executors.newSingleThreadExecutor();
        executorSA.execute(() -> {
            while(true){
                //车速辅助
                sendOutSpeedAuxStatusNotice();
                sleepSeconds(WEBSOCKET_SPEED_AUX_STATUS_INTERVAL_SECONDS);
            }
        });
        //每3秒发送一次单体电池电压
        ExecutorService executorVCell = Executors.newSingleThreadExecutor();
        executorVCell.execute(() -> {
            while(true){
                //电池电压
                sendOutCSCVCellNotice();
                sleepSeconds(WEBSOCKET_CSC_VCELL_INTERVAL_SECONDS);
            }
        });
    }

    //每500毫秒轮询并更新Vehicle Hash
    static void updateVehicleSnapShotManager(){
        ExecutorService executorVS = Executors.newSingleThreadExecutor();
        executorVS.execute(() -> {
            while(true){
                //Vehicle SnapShot
                updateVehicleSnapShot(REDIS_VEHICLE_HASH_NAME);
                sleepMilliSeconds(REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS);
            }
        });
        //每500毫秒查询是否高压电发生变化
        ExecutorService executorPO = Executors.newSingleThreadExecutor();
        executorPO.execute(() -> {
            while(true){
                //高压电发生变化后要做的事情
                // 如：断电后记录下电时间、驾驶循环、触发驾驶循环和重置驾驶模式
                // 如：上电后记录上电时间
                dealStaffWhenPowerChanged();
                sleepMilliSeconds(REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS);
            }
        });
    }

    //每隔5分钟查询天气，缺省是上海 shanghai
    static void queryWeatherManager(String location){
        ExecutorService executorWM = Executors.newSingleThreadExecutor();
        executorWM.execute(() -> {
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
        startRestfulService();
        queryWeatherManager(WEATHER_LOCATION);
        startPushMessageService();

    }
}
