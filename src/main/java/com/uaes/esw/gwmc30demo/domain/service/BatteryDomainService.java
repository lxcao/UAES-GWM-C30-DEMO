package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.B1CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.entity.can.B2CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus.BatteryStatusNotice;
import com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils;

import static com.uaes.esw.gwmc30demo.domain.repository.battery.IBatteryRepository.getB1CanMessageFromRedis;
import static com.uaes.esw.gwmc30demo.domain.repository.battery.IBatteryRepository.getB2CanMessageFromRedis;

public interface BatteryDomainService {
    static BatteryStatusNotice createBatteryStatus(){
        B1CanMessage b1CanMessage = getB1CanMessageFromRedis();
        B2CanMessage b2CanMessage = getB2CanMessageFromRedis();
        BatteryStatusNotice batteryStatusNotice = BatteryStatusNotice.builder()
                .Pack_Curr_BMS(b1CanMessage.getPack_Curr_BMS())
                .Pack_Volt_BMS(b1CanMessage.getPack_Volt_BMS())
                .Pack_Soc_BMS(b1CanMessage.getPack_Soc_BMS())
                .Pack_Temp_BMS(b1CanMessage.getPack_Temp_BMS())
                .Pack_ChrgSts_BMS(b1CanMessage.getPack_ChrgSts_BMS())
                .Pack_ChrgReTime_BMS(b2CanMessage.getPack_ChrgReTime_BMS())
                .dateTime(DateTimeUtils.getDateTimeString()).build();
        return batteryStatusNotice;
    }
}
