package com.wrh.meteo.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

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

}