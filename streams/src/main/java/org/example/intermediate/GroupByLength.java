package org.example.intermediate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByLength {
    public static void main(String[] args) {
        List<String> items = Arrays.asList("apple", "bat", "ball", "cat", "banana", "orange","goat","dog");
        Map<Integer, List<String>> groupByLentghItems = items
                .stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println(groupByLentghItems);
    }
}
