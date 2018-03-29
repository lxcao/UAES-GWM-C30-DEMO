package com.uaes.esw.gwmc30demo.infrastructure.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateTimeUtils {
    static String getDateTimeString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse(LocalDateTime.now().toString(), formatter);
        return parsedDateTime.toString();
    }
}
