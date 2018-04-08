package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.queryESCustomer;

public interface QueryEnergySavingByCustomer {
    static String queryESByCustomer(String reqString){
        return queryESCustomer(reqString);
    }
}
