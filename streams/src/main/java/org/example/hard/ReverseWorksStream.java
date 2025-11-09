package org.example.hard;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ReverseWorksStream {
    public static void main(String[] args) {
        String sentence = "Java streams are powerful";

        String reversedSentence = Arrays.stream(sentence.split(" "))
                .map(word -> new StringBuilder(word).reverse().toString())
                .collect(Collectors.joining(" "));
        System.out.println(reversedSentence);

    }
}
