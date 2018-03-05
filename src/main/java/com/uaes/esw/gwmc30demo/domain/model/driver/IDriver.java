package com.uaes.esw.gwmc30demo.domain.model.driver;

public interface IDriver {
    //注册
    boolean register(Driver driver);
    //登录
    boolean login(Driver driver);
    //等出
    boolean logout(Driver driver);
}
