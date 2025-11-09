package org.example.supereasy;

import java.util.Arrays;
import java.util.List;

public class CountGreaterNumbers {
    public static void main(String[] args) {
        List<Integer> number = Arrays.asList(1, 2, 4, 4, 5, 6, 7, 3, 78, 548, 45);
        long count = number.stream().filter(n -> n > 10).count();
        System.out.println(count);
    }
}
