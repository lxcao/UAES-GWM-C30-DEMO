package com.uaes.esw.gwmc30demo.domain.repository.vehicle;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.*;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.DrivingMode;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.Vehicle;
import com.uaes.esw.gwmc30demo.domain.model.entity.weather.Weather;
import com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus.BatteryBalanceInstruction;
import com.uaes.esw.gwmc30demo.domain.repository.drivingMode.IDrivingModeRepository;
import com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility;
import com.uaes.esw.gwmc30demo.infrastructure.kafka.KafkaProducerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.PERCENTAGE;
import static com.uaes.esw.gwmc30demo.constant.InfraKafkaConstants.*;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.domain.repository.can.ICanRepository.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.*;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.getLastOneStringFromZset;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.getDateTimeNowTimeStamp;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.batteryBalanceLogInfo;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.commonLogInfo;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.drivingModelLogInfo;

public interface IVehicleRepository {
    //得到车辆的当前快照
     static Vehicle getVehicleSnapshot(String vinCode){
        Map<String, String> vehicleHashSet = hGetAll(REDIS_VEHICLE_HASH_NAME);
        Battery c30Battery = Battery.builder()
                .soc(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_BATTERY_SOC)))
                .balanceStatus(Integer.parseInt(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_BATTERY_BALANCE_STATUS)))
                .chargingStatus(Integer.parseInt(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_BATTERY_CHARGING_STATUS)))
                .chargingTime(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_BATTERY_CHARGING_TIME)))
                .current(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_BATTERY_CURRENT)))
                .voltage(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_BATTERY_VOLTAGE)))
                .socMax(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_BATTERY_SOC_MAX)))
                .socMin(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_BATTERY_SOC_MIN)))
                .temperature(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_BATTERY_TEMPERATURE)))
                .hvPower(Integer.parseInt(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_HVPOWER)))
                .remainDistance(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_REMAIN_DISTANCE)))
                .tmOperMod(Integer.parseInt(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_TM_OPERATION_MODE)))
                .dcdcOperMod(Integer.parseInt(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_DCDC_OPERATION_MODE)))
                .ptcPCnsmptn(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_PTC_POWER_CONSUME)))
                .cmpPCnsmptn(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_CMP_POWER_CONSUME))).build();
        Vehicle c30Vehicle = Vehicle.builder()
                .battery(c30Battery)
                .vin(vinCode)
                .maxMileage(Double.parseDouble(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_MAXMILEAGE))).build();

/*         Battery c30Battery = Battery.builder().soc(getLastOneSOCInZset()).build();
         Vehicle c30Vehicle = Vehicle.builder().battery(c30Battery).vin(vinCode)
                 .maxMileage(GWM_C30_MAX_MILEAGE).build();*/

        return c30Vehicle;
    }

    //把从车上收到的soc信息存放到Vehicle的hash中
     static void setSOC2VehicleSnapshot(String vehicleHashName, String soc){
         setValue2HashField(vehicleHashName,REDIS_VEHICLE_HASH_KEY_BATTERY_SOC,soc);
    }

    //把从车上收到的电池信息存放到Vehicle的hash中
    static void setBatteryInfo2VehicleSnapshot(String vehicleHashName, Map<String, String> batteryHash){
        setValues2HashFields(vehicleHashName,batteryHash);
    }

    //轮询各个CAN的zset，取到最新的值，存放到Vehicle的hash中
    static void updateVehicleSnapShot(String vehicleHashName){
        setBatteryInfo2VehicleSnapshot(vehicleHashName, getLastOneBatteryInfoFromZset());
    }

    //得到最新的battery信息
    static Map<String, String> getLastOneBatteryInfoFromZset(){
         Map<String, String> batteryHash = new HashMap<>();

        B1CanMessage b1CanMessage = getLastBMSB1CanMessageFromRedis();
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_BATTERY_SOC,
                String.valueOf(b1CanMessage.getPack_Soc_BMS() *PERCENTAGE));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_BATTERY_CURRENT,
                String.valueOf(b1CanMessage.getPack_Curr_BMS()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_BATTERY_VOLTAGE,
                String.valueOf(b1CanMessage.getPack_Volt_BMS()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_BATTERY_TEMPERATURE,
                String.valueOf(b1CanMessage.getPack_Temp_BMS()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_BATTERY_BALANCE_STATUS,
                String.valueOf(b1CanMessage.getPack_BalcSts_BMS()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_BATTERY_CHARGING_STATUS,
                String.valueOf(b1CanMessage.getPack_ChrgSts_BMS()));
        B2CanMessage b2CanMessage = getLastBMSB2CanMessageFromRedis();
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_BATTERY_SOC_MAX,
                String.valueOf(b2CanMessage.getPack_CellSocMax_BMS()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_BATTERY_SOC_MIN,
                String.valueOf(b2CanMessage.getPack_CellSocMin_BMS()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_BATTERY_CHARGING_TIME,
                String.valueOf(b2CanMessage.getPack_ChrgReTime_BMS()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_HVPOWER,
                String.valueOf(getLastVCU73MessageFromRedis().getHV_PowerOn()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_REMAIN_DISTANCE,
                String.valueOf(getLastVCU294CanMessageFromRedis().getVCU_RemainDistance()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_TM_OPERATION_MODE,
                String.valueOf(getLastVCUFBCanMessageFromRedis().getTM_OperMod()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_DCDC_OPERATION_MODE,
                String.valueOf(getLastVCUFACanMessageFromRedis().getDCDC_OperMod()));
        VCU29DCanMessage vcu29DCanMessage = getLastVCU29DCanMessageFromRedis();
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_PTC_POWER_CONSUME,
                String.valueOf(vcu29DCanMessage.getPTC_PowerCnsmptn()));
        batteryHash.put(REDIS_VEHICLE_HASH_KEY_CMP_POWER_CONSUME,
                String.valueOf(vcu29DCanMessage.getCMP_PowerCnsmptn()));
        return batteryHash;
    }

    //得到最新的SOC
    static double getLastOneSOCInZset(){
            double pack_Soc_BMS = 0.0;
            String lastString = getLastOneStringFromZset(REDIS_BMS_B1_ZSET);
            B1CanMessage b1CanMessage = transferFromJSON2Object(lastString,B1CanMessage.class);
            double soc = b1CanMessage.getPack_Soc_BMS();
            pack_Soc_BMS = soc *PERCENTAGE;

        return pack_Soc_BMS;
    }

    //发送Current DrivingMode到Vehicle
    static void sendCurrentDM2Vehicle(Driver driver){
        DrivingMode currentDM = IDrivingModeRepository.getDrivingMode(driver.getCurrentDM(),driver);
        String currentDMStr = JSONUtility.transferFromObject2JSON(currentDM);
        drivingModelLogInfo("Send CurrentDM2Vehicle="+currentDMStr);
        //send to kafka
        KafkaProducerFactory.sendMessage(KAFKA_DIRVING_MODE_TOPIC,KAFKA_CONFIG_CURRENT_DM_KEY,currentDMStr);
        //save to redis
        inputValue2ZSET(REDIS_CURRENT_DRIVING_MODE_ZSET, getDateTimeNowTimeStamp(), currentDMStr);
    }

    //发送Default DrivingMode到Vehicle
    static void sendDefaultDM2Vehicle(Driver driver){
        DrivingMode defaultDM = IDrivingModeRepository.getDrivingMode(driver.getDefaultDM(),driver);
        String defaultDMStr = JSONUtility.transferFromObject2JSON(defaultDM);
        drivingModelLogInfo("Send DefaultDM2Vehicle="+defaultDMStr);
        //send to kafka
        KafkaProducerFactory.sendMessage(KAFKA_DIRVING_MODE_TOPIC,KAFKA_CONFIG_DEFAULT_DM_KEY,defaultDMStr);
    }

    //发送Normal DrivingMode到Vehicle
    static void sendNormalDM2Vehicle(Driver driver){
         DrivingMode normalDM = IDrivingModeRepository.getVehicleNORDrivingMode();
         String normalDMStr = JSONUtility.transferFromObject2JSON(normalDM);
        drivingModelLogInfo("Send NormalDM2Vehicle="+normalDMStr);
        //send to kafka
        KafkaProducerFactory.sendMessage(KAFKA_DIRVING_MODE_TOPIC,KAFKA_CONFIG_NORMAL_DM_KEY,normalDMStr);
    }

    //发送Weather到Vehicle
    static void sendWeather2Vehicle(Weather weather){
         String weatherStr = JSONUtility.transferFromObject2JSON(weather);
        commonLogInfo("Send Weather2Vehicle="+weatherStr);
        //send to kafka
        KafkaProducerFactory.sendMessage(KAFKA_WEATHER_TOPIC,KAFKA_WEATHER_KEY,weatherStr);
    }

    //根据V60的帧格式，发送Weather和DrivingModeConfigure
    static void sendWeatherAndDMConfig2Vehicle(Weather weather){
         String currentDMStr = getLastOneStringFromZset(REDIS_CURRENT_DRIVING_MODE_ZSET);
         DrivingMode currentDM = transferFromJSON2Object(currentDMStr, DrivingMode.class);
        VCU60CanMessage vcu60CanMessage = VCU60CanMessage.builder()
                .Regen_Level(currentDM.getDrivingModeConfigure().getEnergyRecovery())
                .Filter_Level(currentDM.getDrivingModeConfigure().getSmoothness())
                .Vmax_Level(currentDM.getDrivingModeConfigure().getMaxSpeed())
                .Acceleration_Level(currentDM.getDrivingModeConfigure().getPowerCorresponding())
                .AccessoryPower_Level(currentDM.getDrivingModeConfigure().getAccessoryPerformance())
                .Whether_Status(weather.getWeatherNow().getWeatherStatus())
                .Environment_Temperature(weather.getWeatherNow().getTemperature())
                .Air_Quality_Value(weather.getAirNow().getAqi())
                .unixtimestamp(getDateTimeNowTimeStamp())
                .build();
        String vcu60CanMessageStr = transferFromObject2JSON(vcu60CanMessage);
        commonLogInfo("sendWeatherAndDMConfig2Vehicle="+vcu60CanMessageStr);
        //send to kafka
        KafkaProducerFactory.sendMessage(KAFKA_WEATHER_TOPIC,KAFKA_WEATHER_KEY,vcu60CanMessageStr);
    }

    //发送电池均衡指令到Vehicle
    static void sendBatteryBalance2Vehicle(BatteryBalanceInstruction batteryBalanceInstruction){
        String batteryBIStr = JSONUtility.transferFromObject2JSON(batteryBalanceInstruction);
        batteryBalanceLogInfo("Send BatteryBI2Vehicle="+batteryBIStr);
        //send to kafka
        KafkaProducerFactory.sendMessage(KAFKA_BATTERYBI_TOPIC,KAFKA_BATTERYBI_KEY,batteryBIStr);
    }

    static int getHVPowerOnStatusNow(){
        VCU73CanMessage vcu73CanMessage = getLastVCU73MessageFromRedis();
        return vcu73CanMessage.getHV_PowerOn();
    }

    static int getHVPowerOnStatusPrevious(){
        VCU73CanMessage vcu73CanMessage = getPreviousVCU73MessageFromRedis();
        return vcu73CanMessage.getHV_PowerOn();
    }
}
