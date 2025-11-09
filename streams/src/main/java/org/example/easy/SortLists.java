package org.example.easy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class SortLists {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 1, 8, 3, 2, 10);
        List<Integer> aseOrder = numbers.stream().sorted().toList();
        System.out.println(aseOrder);
        List<Integer> dscOrder = numbers.stream().sorted(Comparator.reverseOrder()).toList();
        System.out.println(dscOrder);
    }
}
