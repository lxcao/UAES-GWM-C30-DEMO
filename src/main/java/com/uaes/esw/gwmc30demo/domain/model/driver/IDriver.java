package com.uaes.esw.gwmc30demo.domain.model.driver;

public interface IDriver {
    //注册
    boolean register(Driver driver);
    //登录
    boolean login(Driver driver);
    //登出
    boolean logout(Driver driver);
    //是否已经登录
    boolean isLogin(Driver driver);
}
