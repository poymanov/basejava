package com.basejava;

import java.util.Arrays;

public class JavaStreamTest {
    public static void main(String[] args) {
        System.out.println(minValue(new int[] {1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[] {9, 8}));
    }

    private static int minValue(int values[]) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (number1, number2) -> (number1 * 10) + number2);
    }
}
