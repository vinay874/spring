package org.example.easy;

import java.util.Arrays;
import java.util.List;

public class AllPositiveExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 6, 7, 1, -6);
        boolean isPostive = numbers.stream().allMatch(a -> a > 0);
        System.out.println(isPostive);
    }
}
