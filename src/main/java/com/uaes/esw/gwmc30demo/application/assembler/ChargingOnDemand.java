package com.uaes.esw.gwmc30demo.application.assembler;

import com.google.gson.Gson;
import com.uaes.esw.gwmc30demo.application.service.JourneyService;
import com.uaes.esw.gwmc30demo.domain.model.charger.Charger;
import com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics.RouteCharging;
import com.uaes.esw.gwmc30demo.domain.model.journey.Journey;
import com.uaes.esw.gwmc30demo.domain.model.journey.Location;
import com.uaes.esw.gwmc30demo.domain.model.journey.Route;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;
import com.uaes.esw.gwmc30demo.domain.service.ChargingDomainService;

import java.util.ArrayList;
import java.util.List;

import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.*;

public class ChargingOnDemand implements JourneyService {

    private Gson gson = new Gson();

    public Journey getJourney(String journeyString){
        Journey journey = gson.fromJson(journeyString, Journey.class);
        return journey;
    }

    public String chargingOnDemandByJourney(String journeyString){
        Journey journey = gson.fromJson(journeyString, Journey.class);
        List<Route> routes = journey.getRoutes();
        //should get from redis
        Battery c30Battery = Battery.builder().soc(2).build();
        Vehicle c30Vehicle = Vehicle.builder().battery(c30Battery).vin("111111")
                .maxMileage(GWM_C30_MAX_MILEAGE).build();
        ChargingDomainService chargingDomainSrv = new ChargingDomainService();
        List <RouteCharging> routeChargingsList = new ArrayList<>();
        for (Route route : routes) {
            RouteCharging routeCharging = chargingDomainSrv.calChargeTimeByChargerType(route, c30Vehicle);
            routeChargingsList.add(routeCharging);
        }
        String routeChargingListJSON = gson.toJson(routeChargingsList);
        return routeChargingListJSON;
    }

    private void testGetJourney(){
        Location sourceLocation = Location.builder().lat("11.111").lng("22.222")
                .locationName("source").build();
        Location destinationLocation = Location.builder().lat("33.333").lng("44.444")
                .locationName("destination").build();
        Route fastestRoute = Route.builder().distance(10).expectTimeConsuming(3600)
                .label("fastest").build();
        Route lessCostRoute = Route.builder().distance(14).expectTimeConsuming(3900)
                .label("lessCost").build();
        Route lessTrafficRoute = Route.builder().distance(16).expectTimeConsuming(6900)
                .label("lessTraffic").build();
        List routeList = new ArrayList();
        routeList.add(fastestRoute);
        routeList.add(lessCostRoute);
        routeList.add(lessTrafficRoute);
        Journey journey = Journey.builder().destination(destinationLocation)
                .source(sourceLocation).routes(routeList).build();

        String jsonObject = gson.toJson(journey);

        System.out.println(jsonObject);

        Journey newJourney = getJourney(jsonObject);
        List<Route> routes = newJourney.getRoutes();
        //should get from redis
        Battery c30Battery = Battery.builder().soc(2).build();
        Vehicle c30Vehicle = Vehicle.builder().battery(c30Battery).vin("111111")
                .maxMileage(GWM_C30_MAX_MILEAGE).build();
        ChargingDomainService chargingDomainSrv = new ChargingDomainService();
        List <RouteCharging> routeChargingsList = new ArrayList<>();
        for (Route route : routes) {
            RouteCharging routeCharging = chargingDomainSrv.calChargeTimeByChargerType(route, c30Vehicle);
            routeChargingsList.add(routeCharging);
        }
        System.out.println(gson.toJson(routeChargingsList));
    }

    public static void main(String[] args) {
        new ChargingOnDemand().testGetJourney();

    }

}
