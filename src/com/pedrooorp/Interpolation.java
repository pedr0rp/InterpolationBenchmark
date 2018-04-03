package com.pedrooorp;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static java.lang.Math.toIntExact;

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
                    BigDecimal y = yA.add(yB.subtract(yA).multiply(x.subtract(xA).divide(xB.subtract(xA)))).setScale(scale, roundingMode);

                    result[i] = new Point<>(x,y);
                    break;
                }
            }
        }

        return result;
    }

    public static Point<Long>[] linear (Point<Long>[] points, long step, int scale) {

        long[] domain = createDomain(points[0].x, points[points.length-1].x, step, scale);
        Point<Long>[] result = new Point[domain.length];

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < points.length; j++) {
                int next  = j+1 < points.length ? j+1 : points.length-1;

                if(domain[i] >= points[j].x && domain[i] <= points[next].x) {
                    long xA = points[j].x;
                    long yA = points[j].y;
                    long xB = points[next].x;
                    long yB = points[next].y;
                    long x = domain[i];

                    long y;

                    if(x-xA < xB-xA) y = yA + ((yB-yA)/(int)Math.pow(10,scale))*((x-xA)/((xB-xA)/(int)Math.pow(10,scale)));
                    else y = yA + (yB-yA)*((x-xA)/(xB-xA));

                    System.out.println("y: " + y);

                    result[i] = new Point<>(x,y);
                    break;
                }
            }
        }

        return result;
    }



    public static Point<BigDecimal>[] polygon (Point<BigDecimal>[] points, BigDecimal step, int scale) {
        step = step.setScale(scale, roundingMode);

        BigDecimal[] domain = createDomain(points[0].x, points[points.length-1].x, step, scale);
        Point<BigDecimal>[] result = new Point[domain.length];

        BigDecimal totalU = BigDecimal.ZERO;
        BigDecimal totalD = BigDecimal.ZERO;
        for(int i = 0; i < result.length; i++) {
            for (int j = 0; j < points.length; j++) {
                 totalU.add (domain[i].subtract(points[j].x));
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

    private static long[] createDomain(long start, long end, long step, int scale) {

        int size = toIntExact((end - start)/ step +1);
        long[] domain = new long[size];

        domain[0] = start;
        for(int i = 1; i < size; i++) {
            start = start + step;
            domain[i] = start;
        }

        return domain;
    }
}
