package com.wrh.meteo.component.lunar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/3/29 16:06
 * @describe
 */
public class Solar {
    public int solarYear;
    public int solarMonth;
    public int solarDay;

    public Solar(int solarYear, int solarMonth, int solarDay) {
        this.solarYear = solarYear;
        this.solarMonth = solarMonth;
        this.solarDay = solarDay;
    }

    @Override
    public String toString() {
        return solarYear + "-" + solarMonth + "-" + solarDay;
    }

    public LocalDate toLocalDate() {
        return LocalDate.of(this.solarYear, this.solarMonth, this.solarDay);
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDate.of(this.solarYear, this.solarMonth, this.solarDay).atTime(LocalTime.now());
    }

}
