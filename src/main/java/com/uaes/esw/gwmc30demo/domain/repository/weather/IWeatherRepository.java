package com.uaes.esw.gwmc30demo.domain.repository.weather;

import com.uaes.esw.gwmc30demo.domain.model.entity.weather.Weather;

public interface IWeatherRepository {
    //查询
    Weather queryWeather();
}
