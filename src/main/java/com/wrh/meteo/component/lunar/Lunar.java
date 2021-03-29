package com.wrh.meteo.component.lunar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/3/29 16:05
 * @describe
 */
public class Lunar {
    public int lunarYear;
    public int lunarMonth;
    public int lunarDay;
    public boolean isleap;

    public Lunar(int lunarYear, int lunarMonth, int lunarDay) {
        this.lunarYear = lunarYear;
        this.lunarMonth = lunarMonth;
        this.lunarDay = lunarDay;
        this.isleap = false;
    }

    @Override
    public String toString() {
        return lunarYear + "-" + lunarMonth + "-" + lunarDay;
    }

    public LocalDate toLocalDate() {
        return LocalDate.of(this.lunarYear, this.lunarMonth, this.lunarDay);
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDate.of(this.lunarYear, this.lunarMonth, this.lunarDay).atTime(LocalTime.now());
    }
}
