package com.pedrooorp;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {

    private static final int SCALE = 4;
    
    private static final BigDecimal MIN = new BigDecimal(0.0f);
    private static final BigDecimal MAX = new BigDecimal(5.0f);
    private static final int QT_POINTS = 5;
    private static final BigDecimal START  = new BigDecimal(0.0f);
    private static final BigDecimal END  = new BigDecimal(5.0f);
    private static final BigDecimal STEP  = new BigDecimal(1f/2);

    private static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;



    public static void main(String[] args) {

        Point<BigDecimal>[] xyBD = new Point[QT_POINTS];
        Point<Long>[] xyL = new Point[QT_POINTS];

        for(int i = 0; i < xyBD.length; i++) {
            xyBD[i] = new Point<>(new BigDecimal(i), randomBigDecimal(MIN, MAX, SCALE));
            xyL[i] = new Point<>(convert(xyBD[i].x), convert(xyBD[i].y));
        }


        Interpolation.roundingMode = ROUNDING_MODE;



        CSVFile fileBD = new CSVFile("BigDecimal", xyBD);
        CSVFile fileL = new CSVFile("Long", xyL);

        Interpolation.linear(xyBD , START, END, STEP, SCALE);

        try {
            fileBD.save();
            fileL.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static long convert(BigDecimal value) {
        return value.scaleByPowerOfTen(value.scale()).longValue();
    }

    private static BigDecimal randomBigDecimal(BigDecimal min, BigDecimal max, int scale) {
        BigDecimal number = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return number.setScale(scale, ROUNDING_MODE);
    }
}
