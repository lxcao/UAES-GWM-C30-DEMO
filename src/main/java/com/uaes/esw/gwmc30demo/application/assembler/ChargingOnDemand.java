package com.uaes.esw.gwmc30demo.application.assembler;

import com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics.RouteCharging;
import com.uaes.esw.gwmc30demo.domain.model.journey.Journey;
import com.uaes.esw.gwmc30demo.domain.model.journey.Location;
import com.uaes.esw.gwmc30demo.domain.model.journey.Route;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.uaes.esw.gwmc30demo.application.service.JourneyService.chargingOnDemandByJourney;
import static com.uaes.esw.gwmc30demo.application.service.JourneyService.getJourney;
import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.*;
import static com.uaes.esw.gwmc30demo.domain.service.ChargingDomainService.calChargeTimeByChargerType;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public class ChargingOnDemand{

    public static String chargingOnDemandByJourneyAssembler(String journeyString){
        //TODO: should be parse by gson to get the vinCode from journeyString
        return chargingOnDemandByJourney("111111", journeyString);
    }


    private void testGetJourney(){
        Location sourceLocation = Location.builder().lat("11.111").lng("22.222")
                .locationName("source").build();
        Location destinationLocation = Location.builder().lat("33.333").lng("44.444")
                .locationName("destination").build();
        Route fastestRoute = Route.builder().distance(100).expectTimeConsuming(3600)
                .label("fastest").build();
        Route lessCostRoute = Route.builder().distance(140).expectTimeConsuming(3900)
                .label("lessCost").build();
        Route lessTrafficRoute = Route.builder().distance(200).expectTimeConsuming(6900)
                .label("lessTraffic").build();
        List routeList = new ArrayList();
        routeList.add(fastestRoute);
        routeList.add(lessCostRoute);
        routeList.add(lessTrafficRoute);
        Journey journey = Journey.builder().destination(destinationLocation)
                .source(sourceLocation).routes(routeList).build();

        String jsonObject = transferFromObject2JSON(journey);

        System.out.println(jsonObject);

        Journey journeyNew = transferFromJSON2Object(jsonObject,Journey.class);
        System.out.println(journeyNew.toString());

        Journey newJourney = getJourney(jsonObject);
        List<Route> routes = newJourney.getRoutes();
        //should get from redis
        Battery c30Battery = Battery.builder().soc(0.5).build();
        Vehicle c30Vehicle = Vehicle.builder().battery(c30Battery).vin("111111")
                .maxMileage(GWM_C30_MAX_MILEAGE).build();
        List <RouteCharging> routeChargingsList = routes.stream()
                .map(route -> calChargeTimeByChargerType(route, c30Vehicle))
                .collect(Collectors.toList());
        System.out.println(transferFromObject2JSON(routeChargingsList));
    }

    public static void main(String[] args) {
        new ChargingOnDemand().testGetJourney();

    }

}
