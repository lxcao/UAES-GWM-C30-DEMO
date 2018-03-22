package com.uaes.esw.gwmc30demo.application.assembler;

import com.uaes.esw.gwmc30demo.domain.model.scenario.logInOut.*;
import com.uaes.esw.gwmc30demo.domain.service.LogInDomainService;
import com.uaes.esw.gwmc30demo.domain.service.LogOutDomainService;
import com.uaes.esw.gwmc30demo.domain.service.RegisterDomainService;

import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface DriverService {
    //注册
    static String register(String registerString){
        RegisterReq registerReq = transferFromJSON2Object(registerString, RegisterReq.class);
        RegisterRes registerRes = RegisterDomainService.registerDomainService(registerReq);
        return transferFromObject2JSON(registerRes);
    }
    //登录
    static String login(String loginString){
        LogInReq logInReq = transferFromJSON2Object(loginString, LogInReq.class);
        LogInRes logInRes = LogInDomainService.logInDomainService(logInReq);

        return transferFromObject2JSON(logInRes);
    }

    //登出
    static String logout(String logoutString){
        LogOutReq logOutReq = transferFromJSON2Object(logoutString, LogOutReq.class);
        LogOutRes logOutRes = LogOutDomainService.logOutDomainService(logOutReq);
        return transferFromObject2JSON(logOutRes);
    }

    //是否已经登录
    static boolean isLogIn(String loginString){
        LogInReq logInReq = transferFromJSON2Object(loginString, LogInReq.class);
        return LogInDomainService.isLogInDomainService(logInReq);
    }

}
