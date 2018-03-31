package com.pedrooorp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final BigDecimal MIN = new BigDecimal(0.0f);
    private static final BigDecimal MAX= new BigDecimal(999999.0f);
    private static final int SCALE = 4;
    private static final int DOMAIN_SIZE = 100;


    public static void main(String[] args) {

        Point<BigDecimal>[] xyBD = new Point[DOMAIN_SIZE];

        for(int i = 0; i < xyBD.length; i++) {
            xyBD[i] = new Point<>(generateRandomBigDecimal(SCALE,  MIN, MAX),generateRandomBigDecimal(SCALE, MIN, MAX));
            System.out.println(xyBD[i]);
        }


    }

    static long convert(BigDecimal value) {
        return  value.scaleByPowerOfTen(value.scale()).longValue();
    }

    static BigDecimal generateRandomBigDecimal(int scale, BigDecimal min, BigDecimal max) {
        BigDecimal number = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return number.setScale(scale, RoundingMode.DOWN);
    }



}
