package org.example.hard;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Top3FrequentWords {
    public static void main(String[] args) {
        String paragraph = "Java is great. Java is object oriented. Java streams are powerful, and java is fun";

        List<Map.Entry<String, Long>> sorted = Arrays.stream(paragraph
                        .toLowerCase()
                        .replaceAll("^a-z\\s", "")
                        .split(" ")
                )
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String,Long>comparingByValue().reversed())
                .toList();

        List<Long> topFrequency = sorted.stream()
                .map(Map.Entry::getValue)
                .distinct()
                .limit(3)
                .toList();

        List<Map.Entry<String, Long>> fWords = sorted.stream().filter(e -> topFrequency.contains(e.getValue())).toList();

        System.out.println(fWords);
    }
}
