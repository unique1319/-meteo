package com.wrh.meteo;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/5 13:46
 * @describe 计算相对湿度
 */
public class ConvertHumidityUtil {

    /*------------ 比湿转相对湿度 ---------------------------------------------------------*/

    /**
     * 根据气温(K)、气压，将比湿转为相对湿度
     *
     * @param q 比湿
     * @param p 气压
     * @param T 开尔文气温（单位：K）
     * @return Uw 相对湿度（单位：%）
     */
    public static double specificConvertHumidityKelvin(double q, double p, double T) {
        double Too = 273.16;
        double Uw, qs, es, esPower; //  Uw:相对湿度; esPower: es = 10^esPower次幂
        esPower = 10.79574 * (1 - (Too / T)) - 5.02800 * Math.log10(T / Too)
                + 1.50475 * Math.pow(10, -4) * (1 - Math.pow(10, (-8.2969 * ((T / Too) - 1))))
                + 0.42873 * Math.pow(10, -3) * (Math.pow(10, (4.76955 * (1 - (Too / T)))) - 1)
                + 0.78614;
        es = Math.pow(10, esPower);
        qs = 0.622 * es / p;
        Uw = q / qs;
        return Uw * 100;
    }

    /**
     * 根据气温(℃)、气压，将比湿转为相对湿度
     *
     * @param q 比湿
     * @param p 气压
     * @param t 摄氏度（单位：℃）
     * @return Uw 相对湿度（单位：%）
     */
    public static double specificConvertHumidity(double q, double p, double t) {
        double T = t + 273.15d;
        return specificConvertHumidityKelvin(q, p, T);
    }

    /*------------ 露点温度转相对湿度 ---------------------------------------------------------*/

    /**
     * @param Td 露点温度（℃，摄氏度）
     * @param t  气温（℃，摄氏度）
     * @return 相对湿度
     */
    public static double dewPointConvertHumidity(double Td, double t) {
        // U为相对湿度,E为水汽压(hpa),Ew为干球温度t所对应的纯水平液面饱和水汽压(hpa)。Td为露点温度(摄氏度)，T1为水的三相点温度(摄氏度)。
        double U, E, Ew, EwPower;
        double Eo = 6.1078d, a = 7.69d, b = 243.92d, T1 = 273.16d;
        double T = 273.15d + t;
        EwPower = 10.79574d * (1 - T1 / T) - 5.02800d * Math.log(T / T1) +
                1.50475d * Math.pow(10, -4) * (1 - Math.pow(10, -8.2969d * (T / T1 - 1))) +
                0.42873d * Math.pow(10, -3) * (Math.pow(10, 4.76955d * (1 - T1 / T) - 1)) +
                0.78614d;
        Ew = Math.pow(10, EwPower);
        E = Eo * Math.pow(10, (a * Td) / (b + Td));
        U = (E / Ew);
        return U * 100;
    }

    /**
     * @param Td 露点温度（K，开尔文）
     * @param t  气温（℃，摄氏度）
     * @return 相对湿度
     */
    public static double dewPointConvertHumidityKelvin(double Td, double t) { // Td露点温度(开尔文)
        double T = Td - 273.15d;
        return dewPointConvertHumidity(T, t);
    }
}
