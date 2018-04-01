package com.pedrooorp;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Interpolation {

    static RoundingMode roundingMode = RoundingMode.DOWN;

    public static Point<BigDecimal>[] linear (Point<BigDecimal>[] points, BigDecimal step, int scale) {

        step = step.setScale(scale, roundingMode);

        BigDecimal[] domain = createDomain(points[0].x, points[points.length-1].x, step, scale);
        Point<BigDecimal>[] result = new Point[domain.length];

       for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < points.length; j++) {
                int next  = j+1 < points.length ? j+1 : points.length-1;

                if(domain[i].compareTo(points[j].x) >= 0 && domain[i].compareTo(points[next].x) <= 0) {
                    BigDecimal xA = points[j].x;
                    BigDecimal yA = points[j].y;
                    BigDecimal xB = points[next].x;
                    BigDecimal yB = points[next].y;
                    BigDecimal x = domain[i];
                    BigDecimal y = yA.add(yB.subtract(yA).multiply(x.subtract(xA).divide(xB.subtract(xA))));

                    result[i] = new Point<>(x,y);
                    break;
                }
            }
        }

        return result;
    }

    private static BigDecimal[] createDomain(BigDecimal start, BigDecimal end, BigDecimal step, int scale) {

        MathContext mc = new MathContext(scale, roundingMode);
        int size = end.subtract(start).divide(step, mc).intValue()+1;
        BigDecimal[] domain = new BigDecimal[size];

        domain[0] = start;
        for(int i = 1; i < size; i++) {
            start = start.add(step).setScale(scale, roundingMode);
            domain[i] = start;
        }

        return domain;
    }
}
