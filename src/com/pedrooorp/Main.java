package com.pedrooorp;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int SCALE = 2;
    
    private static final BigDecimal MIN = new BigDecimal(0.0f);
    private static final BigDecimal MAX = new BigDecimal(10.0f);
    private static final int QT_POINTS = 3;
    private static final BigDecimal STEP  = new BigDecimal(1f);

    private static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;

    public static void main(String[] args) {

        List<CSVFile> files = new ArrayList<>();

        Point<BigDecimal>[] xyBD = new Point[QT_POINTS];
        Point<Long>[] xyL = new Point[QT_POINTS];

        for(int i = 0; i < xyBD.length; i++) {
            xyBD[i] = new Point<>(new BigDecimal(i*2).setScale(SCALE), randomBigDecimal(MIN, MAX, SCALE));
            xyL[i] = new Point<>(convert(xyBD[i].x), convert(xyBD[i].y));
        }

        Interpolation.roundingMode = ROUNDING_MODE;

        files.add(new CSVFile("BigDecimal", xyBD));
        files.add(new CSVFile("Long", xyL));
        files.add(new CSVFile("linearInterpolationBD", Interpolation.linear(xyBD, STEP, SCALE)));
        files.add(new CSVFile("linearInterpolationL", Interpolation.linear(xyL, STEP.scaleByPowerOfTen(SCALE).intValue(), SCALE)));

        try {
            for(int i = 0; i < files.size(); i++) {
                files.get(i).save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static long convert(BigDecimal value) { return value.scaleByPowerOfTen(value.scale()).longValue(); }

    private static BigDecimal randomBigDecimal(BigDecimal min, BigDecimal max, int scale) {
        BigDecimal number = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return number.setScale(scale, ROUNDING_MODE);
    }
}
