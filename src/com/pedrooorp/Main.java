package com.pedrooorp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {

    public static void main(String[] args) {
        BigDecimal[] array = generateArray(100, 5, new BigDecimal(0.0f), new BigDecimal(100.0f));

        printArray(array);

    }

    static BigDecimal[] generateArray(int size, int scale, BigDecimal min, BigDecimal max) {
        BigDecimal[] array = new BigDecimal[size];
        for(int i = 0; i < size; i++) array[i] = generateRandomBigDecimal(min, max, scale);
        return array;
    }

    static BigDecimal generateRandomBigDecimal(BigDecimal min, BigDecimal max, int scale) {
        BigDecimal number = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return number.setScale(scale, RoundingMode.DOWN);
    }

    static void printArray(BigDecimal[] array) {
        for(int i = 0; i < array.length; i++) System.out.println(array[i]);
    }
}
