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

package com.uaes.esw.gwmc30demo.constant;

public class InfraKafkaConstants {
    public final static String KAFKA_CONFIG_BOOTSTRAP_SERVERS_CONFIG =
            //"emr-worker-1.cluster-47779:9092,emr-worker-2.cluster-47779:9092";
            "106.14.137.89:9093,47.100.207.222:9093,47.100.201.156:9093";
    public final static String KAFKA_CONFIG_GROUP_ID_CONFIG = "gwm-c30-001";
    public final static String KAFKA_CONFIG_ENABLE_AUTO_COMMIT_CONFIG = "true";
    public final static String KAFKA_CONFIG_AUTO_COMMIT_INTERVAL_MS_CONFIG = "1000";
    public final static String KAFKA_CONFIG_SESSION_TIMEOUT_MS_CONFIG = "30000";
    public final static String KAFKA_CONFIG_SERIALIZER_CLASS_CONFIG =
            "org.apache.kafka.common.serialization.StringSerializer";
    public final static String KAFKA_CONFIG_DESERIALIZER_CLASS_CONFIG =
            "org.apache.kafka.common.serialization.StringDeserializer";
    public final static String KAFKA_CONFIG_AUTO_OFFSET_RESET_CONFIG = "earliest";
    public final static String KAFKA_CONFIG_CURRENT_DM_KEY = "currentDM";
    public final static String KAFKA_CONFIG_DEFAULT_DM_KEY = "defaultDM";
    public final static String KAFKA_CONFIG_NORMAL_DM_KEY = "normalDM";
    public final static String KAFKA_CONFIG_CLIENT_ID = "clientID-c30-001";
    public final static String KAFKA_DIRVING_MODE_TOPIC = "gwm-c30-dm";
    public final static String KAFKA_WEATHER_TOPIC = "gwm-c30-weather";
    public final static String KAFKA_WEATHER_KEY = "weather";
    public final static String KAFKA_BATTERYBI_TOPIC = "gwm-c30-batterybi";
    public final static String KAFKA_BATTERYBI_KEY = "batterybi";
    public final static String KAFKA_VCU_73_TOPIC = "gwm-c30-can-vcu-73";
}
