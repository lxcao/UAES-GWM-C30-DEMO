package com.uaes.esw.gwmc30demo;

import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.startVehicleDataService;
import static com.uaes.esw.gwmc30demo.infrastructure.http.HttpFactory.startApplicationService;

public class GWMC30DemoFactory {
    public static void main(String[] args) {
        startVehicleDataService();
        startApplicationService();
    }
}
