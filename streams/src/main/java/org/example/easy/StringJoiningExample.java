package org.example.easy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringJoiningExample {
    public static void main(String[] args) {
        List<String> furits = Arrays.asList("Apple", "Banana", "Orange");
        String jstr = furits.stream().collect(Collectors.joining());
        System.out.println(jstr);
        String jdstr = furits.stream().collect(Collectors.joining(":"));
        System.out.println(jdstr);
        String lstr = furits.stream().collect(Collectors.joining(",", "[", "]"));
        System.out.println(lstr);
    }
}
