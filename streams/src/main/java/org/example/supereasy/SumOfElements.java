package org.example.supereasy;


import java.util.Arrays;
import java.util.List;

public class SumOfElements {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum);
        Integer prod = numbers.stream().reduce(1, (a,b)->a*b);
        System.out.println(prod);


    }
}
