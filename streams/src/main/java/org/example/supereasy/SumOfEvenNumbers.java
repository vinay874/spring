package org.example.supereasy;

import java.util.Arrays;
import java.util.List;

public class SumOfEvenNumbers {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sumOfEvenNumebrs = numbers.stream().filter(n -> n % 2 == 0).reduce(0, (a, b) -> a + b);
        System.out.println(sumOfEvenNumebrs);
    }
}
