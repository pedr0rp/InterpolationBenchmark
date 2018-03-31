package com.pedrooorp;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Interpolation {

    static RoundingMode roundingMode = RoundingMode.DOWN;

    public static Point<BigDecimal>[] linear (Point<BigDecimal>[] points, BigDecimal start, BigDecimal end, BigDecimal step, int scale) {

        start = start.setScale(scale, roundingMode);
        end = end.setScale(scale, roundingMode);
        step = step.setScale(scale, roundingMode);

        createDomain(start, end, step, scale);

        return null;
    }

    private static BigDecimal[] createDomain(BigDecimal start, BigDecimal end, BigDecimal step, int scale) {

        MathContext mc = new MathContext(scale, roundingMode);

        int size = end.subtract(start).divide(step, mc).intValue() + 2;
        BigDecimal[] domain = new BigDecimal[size];

        domain[0] = start;
        System.out.println(domain[0]);
        for(int i = 1; i < size; i++) {
            start = start.add(step).setScale(scale, roundingMode);
            domain[i] = start;
            System.out.println(domain[i]);
        }

        return domain;
    }
}
