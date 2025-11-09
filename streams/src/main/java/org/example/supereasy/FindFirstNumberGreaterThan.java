package org.example.supereasy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FindFirstNumberGreaterThan {
    public static void main(String[] args) {
       List<Integer> numbers = Arrays.asList(1,2,3,14,5,6,7,8,9,10,12);

        Optional<Integer> gNumber = numbers.stream().filter(n -> n > 10)
                .sorted()
                .findFirst();

        System.out.println(gNumber.get());
    }
}
