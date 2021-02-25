package com.wrh.meteo.read;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/25 9:16
 * @describe
 */
class ReadNcFileTest {

    @Test
    void read() {
        String filePath = "C:\\Users\\Administrator\\Desktop\\test\\P_CMPA_NRT_CHN_0P05_HOR-PRE-2018010104.nc";
        ReadNcFile.read(new File(filePath), "rain");
    }
}