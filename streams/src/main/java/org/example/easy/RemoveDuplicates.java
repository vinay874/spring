package org.example.easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class RemoveDuplicates {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 2, 8, 2, 3, 5, 9, 3);
        HashSet<Integer> set = new HashSet<>(numbers);
        System.out.println(set);

        List<Integer> list = numbers.stream().distinct().toList();
        System.out.println(list);
    }
}
