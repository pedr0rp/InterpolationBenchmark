package com.pedrooorp;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {

    private static final BigDecimal MIN = new BigDecimal(0.0f);
    private static final BigDecimal MAX= new BigDecimal(100.0f);
    private static final int SCALE = 4;
    private static final int DOMAIN_SIZE = 100;


    public static void main(String[] args) {

        Point<BigDecimal>[] xyBD = new Point[DOMAIN_SIZE];
        Point<Long>[] xyL = new Point[DOMAIN_SIZE];

        for(int i = 0; i < xyBD.length; i++) {
            xyBD[i] = new Point<>(new BigDecimal(i), randomBigDecimal(SCALE, MIN, MAX));
            xyL[i] = new Point<>(convert(xyBD[i].x), convert(xyBD[i].y));
        }

        CSVFile fileBD = new CSVFile("BigDecimal", xyBD);
        CSVFile fileL = new CSVFile("Long", xyL);

        try {
            fileBD.save();
            fileL.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static long convert(BigDecimal value) {
        return value.scaleByPowerOfTen(value.scale()).longValue();
    }

    static BigDecimal randomBigDecimal(int scale, BigDecimal min, BigDecimal max) {
        BigDecimal number = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return number.setScale(scale, RoundingMode.DOWN);
    }
}
