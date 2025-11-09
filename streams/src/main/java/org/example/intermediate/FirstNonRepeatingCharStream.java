package org.example.intermediate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FirstNonRepeatingCharStream {
    public static void main(String[] args) {
        String str = "swiss";
        Optional<Map.Entry<Character, Long>> firstNonRepeatChar = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1).findFirst();
        System.out.println(firstNonRepeatChar);
    }
}
