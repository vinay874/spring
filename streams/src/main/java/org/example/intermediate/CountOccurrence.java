package org.example.intermediate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CountOccurrence {
    public static void main(String[] args) {
        List<String> items = Arrays.asList("apple", "bat", "ball", "apple", "banana", "goat","goat","goat");
        Map<String, Long> groupByOccurence = items
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(groupByOccurence);
    }
}
