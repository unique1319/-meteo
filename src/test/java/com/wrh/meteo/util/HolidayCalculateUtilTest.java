package com.wrh.meteo.util;

import com.wrh.meteo.component.LunarCalendar;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/25 16:22
 * @describe
 */
class HolidayCalculateUtilTest {

    @Test
    void calcHolidayList() {
        List<HolidayCalculateUtil.Holiday> holidayList = HolidayCalculateUtil.calcHolidayList(2021);
        System.out.println(holidayList.toString());
    }

    @Test
    void getLunarCalendar() {
        LunarCalendar lunarCalendar = new LunarCalendar(2021,1,25);
        System.out.println(lunarCalendar.toString());
    }

    @Test
    void getSolarCalendar() {
        LocalDateTime localDateTime = HolidayCalculateUtil.lunar2Solar(2021,1,14);
        System.out.println(localDateTime.toString());
    }
}