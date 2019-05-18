package com.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaStreamTest {
    public static void main(String[] args) {
        System.out.println(minValue(new int[] {1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[] {9, 8}));

        System.out.println(oddOrEven(Arrays.asList(1, 2, 3)));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 1)));
    }

    private static int minValue(int values[]) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (number1, number2) -> (number1 * 10) + number2);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int listSum = integers.stream().reduce(0, Integer::sum);
        boolean isSumEven = listSum % 2 == 0;

        return integers.stream()
                .filter(i -> {
                    boolean isNumberEven = i % 2 == 0;

                    if (isSumEven && isNumberEven) {
                        return false;
                    } else if (!isSumEven && !isNumberEven) {
                        return false;
                    } else {
                        return true;
                    }
                })
                .collect(Collectors.toList());
    }
}
