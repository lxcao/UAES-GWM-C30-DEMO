package com.uaes.esw.gwmc30demo.infrastructure.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.*;

public interface DateTimeUtils {
    static String getDateTimeString(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER));
    }

    static long transfer2UnixTime(String dateTime){
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER))
                .atZone(ZoneId.of(ZONE_ID)).toInstant().toEpochMilli();
    }

    static long getTodayBeginUnixTime(){
        String beginToday = LocalDate.now().toString() + SPACE + TIME_BEGIN;
        return transfer2UnixTime(beginToday);
    }

    static long getBeforeOneWeekBeginUnixTime(){
        String beginBeforeOneWeek = LocalDate.now().plusWeeks(-1).toString() + SPACE + TIME_BEGIN;
        return transfer2UnixTime(beginBeforeOneWeek);
    }

    static long getTodayEndUnixTime(){
        String endToday = LocalDate.now().toString() + SPACE + TIME_END;
        return transfer2UnixTime(endToday);
    }

    static long getDateTimeNowTimeStamp(){
        return LocalDateTime.now().atZone(ZoneId.of(ZONE_ID)).toInstant().toEpochMilli();
    }

    static void sleepSeconds(int second){
        try{
            TimeUnit.SECONDS.sleep(second);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    static void sleepMinutes(int minute){
        try{
            TimeUnit.MINUTES.sleep(minute);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    static void sleepMilliSeconds(int milliSecond){
        try{
            TimeUnit.MILLISECONDS.sleep(milliSecond);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
