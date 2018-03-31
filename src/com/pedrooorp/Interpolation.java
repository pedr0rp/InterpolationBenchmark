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

        BigDecimal[] domain = createDomain(start, end, step, scale);
        Point<BigDecimal>[] result = new Point[domain.length];

        System.out.println(domain.length);

        for(int i = 0; i < result.length-1; i++) {

            BigDecimal x = domain[i];

            for(int j = 0; j < points.length; j++) {

                int next = j+1;
                if(next >= points.length) {
                    next--;
                }


                //System.out.println(domain[i] + ">=" + points[j].x + " && " + domain[i] + " <= " +points[next].x);

                if(domain[i].compareTo(points[j].x) >= 0 && domain[i].compareTo(points[next].x) <= 0) {
                    BigDecimal xa = points[j].x;
                    BigDecimal ya = points[j].y;
                    BigDecimal xb = points[next].x;
                    BigDecimal yb = points[next].y;
                    BigDecimal y = ya.add(yb.subtract(ya).multiply(x.subtract(xa).divide(xb.subtract(xa))));

                    result[i] = new Point<>(x,y);
                    //System.out.println(">>>> " +i);
                    break;
                }

            }
        }

        for(int i = 0; i < result.length; i++) {
            System.out.println(i + "  " + result[i]);
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
