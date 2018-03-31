package com.pedrooorp;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {

    private static final int SCALE = 4;
    
    private static final BigDecimal MIN = new BigDecimal(0.0f);
    private static final BigDecimal MAX= new BigDecimal(100.0f);
    private static final int QT_POINTS = 100;
    private static final BigDecimal START  = new BigDecimal(0.0f);
    private static final BigDecimal END  = new BigDecimal(100.0f);
    private static final BigDecimal STEP  = new BigDecimal(1f/3);

    private static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;



    public static void main(String[] args) {

        Point<BigDecimal>[] xyBD = new Point[QT_POINTS];
        Point<Long>[] xyL = new Point[QT_POINTS];

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

        Interpolation.roundingMode = ROUNDING_MODE;
        Interpolation.linear(xyBD , START, END, STEP, SCALE);

    }

    static long convert(BigDecimal value) {
        return value.scaleByPowerOfTen(value.scale()).longValue();
    }

    static BigDecimal randomBigDecimal(int scale, BigDecimal min, BigDecimal max) {
        BigDecimal number = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return number.setScale(scale, ROUNDING_MODE);
    }
}
