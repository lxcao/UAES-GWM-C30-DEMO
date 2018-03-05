package com.uaes.esw.gwmc30demo.domain.repository.driver;

import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;

public interface IDriverRepository {
    //是否存在
    boolean isExist(Driver driver);
    //增加
    boolean createDriver(Driver driver);
    //删除
    boolean deleteDriver(Driver driver);
    //更新
    boolean updateDriver(Driver driver);
    //查询
    Driver queryDriver(String cellPhone);
}
