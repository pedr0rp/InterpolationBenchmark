package com.pedrooorp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final int MIN = 0;
    private static final int MAX = 999999;
    private static final int SCALE = 4;
    private static final int DOMAIN_SIZE = 100;


    public static void main(String[] args) {
        BigDecimal[] xBD  = generateArray(DOMAIN_SIZE , SCALE, new BigDecimal(MIN), new BigDecimal(MAX));
        BigDecimal[] yBD  = generateArray(DOMAIN_SIZE , SCALE, new BigDecimal(MIN), new BigDecimal(MAX));

        printXY(xBD, yBD);

        long[] xL = convert(xBD);
        long[] yL = convert(yBD);

        printXY(xL, yL);

    }

    static BigDecimal[] generateArray(int size, int scale, BigDecimal min, BigDecimal max) {
        BigDecimal[] array = new BigDecimal[size];
        for(int i = 0; i < size; i++) array[i] = generateRandomBigDecimal(scale, min, max);
        return array;
    }

    static long[] convert(BigDecimal[] array) {

        long[] newArray = new long[array.length];

        if(array.length > 1) {
            int scale = array[0].scale();
            for(int i = 0; i < array.length; i++) {
                newArray[i] = array[i].scaleByPowerOfTen(scale).longValue();
            }
        }

        return newArray;
    }

    static BigDecimal generateRandomBigDecimal(int scale, BigDecimal min, BigDecimal max) {
        BigDecimal number = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return number.setScale(scale, RoundingMode.DOWN);
    }

    static void printXY(BigDecimal[] x, BigDecimal[] y) {
        for(int i = 0; i < x.length; i++) System.out.println("X " + x[i] + " Y " + y[i]);
    }

    static void printXY(long[] x, long[] y) {
        for(int i = 0; i < x.length; i++) System.out.println("X " + x[i] + " Y " + y[i]);
    }
}
