package org.example.intermediate;


import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MostFrequentChar {
    public static void main(String[] args) {
        String input = "banana";
        Map.Entry<Character, Long> mostFrequentChar = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
        System.out.println(mostFrequentChar);
    }
}
