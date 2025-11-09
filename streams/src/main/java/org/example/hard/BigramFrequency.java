package org.example.hard;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BigramFrequency {
    public static void main(String[] args) {
        String sentence = "Java is great and Java is fun. Java is powerful!";

        String[] words = sentence
                        .toLowerCase()
                        .replaceAll("^a-z\\s", "")
                        .split(" ");
        List<Map.Entry<String, Long>> frequencyBigrams = IntStream.range(0, words.length - 1)
                .mapToObj(i -> words[i] + " " + words[i + 1])
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() >= 2)
                .toList();

        System.out.println(frequencyBigrams);
    }
}
