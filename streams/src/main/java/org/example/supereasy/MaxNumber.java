package org.example.supereasy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MaxNumber {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 10, 2, 8, 20);
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        System.out.println(max);
    }
}
