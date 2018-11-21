/*
 * Copyright (c) 2004- 2018 UAES-ESW
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uaes.esw.gwmc30demo.test.domain.repository.weather;

import org.junit.Test;

import static com.uaes.esw.gwmc30demo.domain.repository.weather.IWeatherRepository.queryAirNow;
import static com.uaes.esw.gwmc30demo.domain.repository.weather.IWeatherRepository.queryWeather;
import static com.uaes.esw.gwmc30demo.domain.repository.weather.IWeatherRepository.queryWeatherNow;
import static org.junit.Assert.*;

public class IWeatherRepositoryTest {

    @Test
    public void tesQueryWeatherNow() {
        queryWeatherNow("shanghai");
    }

    @Test
    public void testQueryAirNow() {
        queryAirNow("shanghai");
    }

    @Test
    public void testQueryWeather() {
        queryWeather("shanghai");
    }
}