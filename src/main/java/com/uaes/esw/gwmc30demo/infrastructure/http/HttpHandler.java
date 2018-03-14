package com.uaes.esw.gwmc30demo.infrastructure.http;

import spark.Spark;

import static com.uaes.esw.gwmc30demo.application.assembler.ChargingOnDemand.chargingOnDemandByJourneyAssembler;
import static com.uaes.esw.gwmc30demo.constant.InfraHttpConstants.*;

public class HttpHandler {
    public static void setRouter(){
        Spark.post(HTTP_URL_CHARGE_ON_DEMAND,(req, res) -> {
            res.type(HTTP_CONFIG_CONTENT_TYPE);
            res.header(HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_NAME, HTTP_CONFIG_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
            System.out.println(req.body());
            String resString = chargingOnDemandByJourneyAssembler(req.body());
            System.out.println(resString);
            return resString;
        });
    }
}
